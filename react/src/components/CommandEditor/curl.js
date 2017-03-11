export const detectCURLinLine = (line) => {
    return line.match(/^\s*?curl\s+(-X[A-Z]+)?\s*['"]?.*?['"]?(\s*$|\s+?-d\s*?['"])/);

};

export const detectCURL = (text) => {
    // returns true if text matches a curl request
    if (!text) return false;
    let separators = ['\\\\n', '\\\\'];
    let tokens = text.split(new RegExp(separators.join('|'), 'g'));
    for (let line of tokens) {
        if (detectCURLinLine(line)) {
            return true;
        }
    }
    return false;
};

export const parseCURL = (text) => {

    let state = 'NONE';
    let out = [];
    let body = [];
    let line = '';
    let lines = text.trim().split("\n");
    let matches;

    let EmptyLine = /^\s*$/;
    let Comment = /^\s*(?:#|\/{2,})(.*)\n?$/;
    let ExecutionComment = /^\s*#!/;
    let ClosingSingleQuote = /^([^']*)'/;
    let ClosingDoubleQuote = /^((?:[^\\"]|\\.)*)"/;
    let EscapedQuotes = /^((?:[^\\"']|\\.)+)/;

    let LooksLikeCurl = /^\s*curl\s+/;
    let CurlVerb = /^\s*-X ?(GET|POST|DELETE)/;

    let HasProtocol = /[\s"']https?:\/\/?/;
    let CurlRequestWithProto = /[\s"']https?:\/\/?[^\/ ]+\/+([^\s"']+)/;
    let CurlRequestWithoutProto = /[\s"'][^\/ ]+\/+([^\s"']+)/;
    let CurlData = /^.?\s(--data|-d)\s*/m;
    let SenseLine = /^\s*(GET|POST|DELETE)\s+\/?(.+)/;

    if (lines.length > 0 && ExecutionComment.test(lines[0])) {
        lines.shift();
    }

    function nextLine() {
        if (line.length > 0) {
            return true;
        }
        if (lines.length == 0) {
            return false;
        }
        line = lines.shift().replace(/[\r\n]+/g, "\n") + "\n";
        return true;
    }

    function unescapeLastBodyEl() {
        let str = body.pop().replace(/\\([\\"'])/g, "$1");
        body.push(str);
    }

    // Is the next char a single or double quote?
    // If so remove it
    function detectQuote() {
        if (line.substr(0, 1) == "'") {
            line = line.substr(1);
            state = 'SINGLE_QUOTE';
        }
        else if (line.substr(0, 1) == '"') {
            line = line.substr(1);
            state = 'DOUBLE_QUOTE';
        }
        else {
            state = 'UNQUOTED';
        }
    }

    // Body is finished - append to output with final LF
    function addBodyToOut() {
        if (body.length > 0) {
            let b = body.join("");
            out.push(JSON.stringify(JSON.parse(b), null, 4));
            body = [];
        }
        state = 'LF';
        out.push("\n");
    }

    // If the pattern matches, then the state is about to change,
    // so add the capture to the body and detect the next state
    // Otherwise add the whole line
    function consumeMatching(pattern) {
        let matches = line.match(pattern);
        if (matches) {
            body.push(matches[1]);
            line = line.substr(matches[0].length);
            detectQuote();
        }
        else {
            body.push(line);
            line = '';
        }
    }

    function parseCurlLine() {
        let verb = 'GET';
        let request = '';
        let matches;
        if (matches = line.match(CurlVerb)) {
            verb = matches[1];
        }

        // JS regexen don't support possesive quantifiers, so
        // we need two distinct patterns
        let pattern = HasProtocol.test(line)
            ? CurlRequestWithProto
            : CurlRequestWithoutProto;

        if (matches = line.match(pattern)) {
            request = matches[1];
        }

        out.push(verb + ' ' + request + "\n");

        if (matches = line.match(CurlData)) {
            line = line.substr(matches[0].length);
            detectQuote();
            if (EmptyLine.test(line)) {
                line = '';
            }
        }
        else {
            state = 'NONE';
            line = '';
            out.push('');
        }
    }

    while (nextLine()) {

        console.log(line);
        if (state == 'SINGLE_QUOTE') {
            consumeMatching(ClosingSingleQuote);
        }

        else if (state == 'DOUBLE_QUOTE') {
            consumeMatching(ClosingDoubleQuote);
            unescapeLastBodyEl();
        }

        else if (state == 'UNQUOTED') {
            consumeMatching(EscapedQuotes);
            if (body.length) {
                unescapeLastBodyEl();
            }
            if (state == 'UNQUOTED') {
                addBodyToOut();
                line = ''
            }
        }

        // the BODY state (used to match the body of a Sense request)
        // can be terminated early if it encounters
        // a comment or an empty line
        else if (state == 'BODY') {
            if (Comment.test(line) || EmptyLine.test(line)) {
                addBodyToOut();
            }
            else {
                body.push(line);
                line = '';
            }
        }

        else if (EmptyLine.test(line)) {
            if (state != 'LF') {
                out.push("\n");
                state = 'LF';
            }
            line = '';
        }

        else if (matches = line.match(Comment)) {
            out.push("#" + matches[1] + "\n");
            state = 'NONE';
            line = '';
        }

        else if (LooksLikeCurl.test(line)) {
            parseCurlLine();
        }

        else if (matches = line.match(SenseLine)) {
            out.push(matches[1] + ' /' + matches[2] + "\n");
            line = '';
            state = 'BODY';
        }
        else if (matches = line.match(CurlData)) {
            line = line.substr(matches[0].length);
            detectQuote();
            if (EmptyLine.test(line)) {
                line = '';
            }
        }

        else {
            out.push(line);
            line = '';
        }
    }

    addBodyToOut();
    return out.join('').trim();
};

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
    let CurlVerb = /\s*-X ?(GET|POST|DELETE)/;

    let HasProtocol = /[\s"']https?:\/\/?/;
    // eslint-disable-next-line
    let CurlRequestWithProto = /[\s"']https?:\/\/?[^\/ ]+\/+([^\s"']+)/;
    // eslint-disable-next-line
    let CurlRequestWithoutProto = / {1}[\s"'][^\/ ]+\/+[^\/ ]+\/+([^\s"']+)/;
    let CurlData = /\s*(--data|-d)\s*/m;

    if (lines.length > 0 && ExecutionComment.test(lines[0])) {
        lines.shift();
    }

    function nextLine() {
        if (line.length > 0) {
            return true;
        }
        if (lines.length === 0) {
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
        if (line.substr(0, 1) === "'") {
            line = line.substr(1);
            state = 'SINGLE_QUOTE';
        }
        else if (line.substr(0, 1) === '"') {
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
            try {
                out.push(JSON.stringify(JSON.parse(b), null, 4));
            } catch (e) {
                out.push(b);
            }
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
        let verb = '';
        let request = '';
        let matches;
        // eslint-disable-next-line
        if (matches = line.match(CurlVerb)) {
            verb = matches[1];
        }

        // JS regexen don't support possesive quantifiers, so
        // we need two distinct patterns
        let pattern = HasProtocol.test(line)
            ? CurlRequestWithProto
            : CurlRequestWithoutProto;

        // eslint-disable-next-line
        if (matches = line.match(pattern)) {
            request = matches[1];
        }

        out.push(verb + ' ' + request + "\n");

        // eslint-disable-next-line
        if (matches = line.match(CurlData)) {
            let index = line.lastIndexOf(matches[0]);
            line = line.substr(index);
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
        // line = '';
    }

    while (nextLine()) {

        if (state === 'SINGLE_QUOTE') {
            consumeMatching(ClosingSingleQuote);
        }

        else if (state === 'DOUBLE_QUOTE') {
            consumeMatching(ClosingDoubleQuote);
            unescapeLastBodyEl();
        }

        else if (state === 'UNQUOTED') {
            consumeMatching(EscapedQuotes);
            if (body.length) {
                unescapeLastBodyEl();
            }
            if (state === 'UNQUOTED') {
                addBodyToOut();
                line = ''
            }
        }

        else if (EmptyLine.test(line)) {
            if (state !== 'LF') {
                out.push("\n");
                state = 'LF';
            }
            line = '';
        }

        // eslint-disable-next-line
        else if (matches = line.match(Comment)) {
            out.push("#" + matches[1] + "\n");
            state = 'NONE';
            line = '';
        }
        // eslint-disable-next-line
        else if (matches = line.match(LooksLikeCurl)) {
            line = line.substr(matches[0].length).trim();
            parseCurlLine();
        }
        // eslint-disable-next-line
        else if (matches = line.match(CurlData)) {
            line = line.substr(matches[0].length);
            detectQuote();
            if (EmptyLine.test(line)) {
                line = '';
            }
        }
        // eslint-disable-next-line
        else if (matches = line.match(CurlVerb)) {
            let verb = matches[1];
            out.push(verb + ' ');
            line = line.substr(matches[1].length).trim();

            let pattern = HasProtocol.test(line)
                ? CurlRequestWithProto
                : CurlRequestWithoutProto;
            // eslint-disable-next-line
            if (matches = line.match(pattern)) {
                let request = matches[1];
                out.push(request + "\n");
            } else {
                state = 'NONE';
                out.push('');
            }
            // line = '';
        }

        else {
            out.push('');
            line = '';
        }
    }

    addBodyToOut();
    return out.join('').trim();
};

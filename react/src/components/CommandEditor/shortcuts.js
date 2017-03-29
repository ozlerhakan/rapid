/**
 * Created by hakan on 23/02/2017.
 */
import * as curl from './curl';

export const apply = (editor) => {

    editor.commands.addCommand({
        name: 'cut-1',
        bindKey: {win: 'Ctrl-X', mac: 'Command-X'},
        exec: function (editor) {

            editor.removeToLineEnd();
            editor.removeToLineStart();
        },
        readOnly: true
    });

    editor.commands.addCommand({
        name: 'ctrl-duplicate',
        bindKey: {win: 'Ctrl-D', mac: 'Command-D'},
        exec: function (editor) {
            editor.copyLinesDown();
        },
        readOnly: true
    });

    editor.commands.addCommand({
        name: 'shift-enter-line',
        bindKey: {win: 'Shift-Enter', mac: 'Shift-Enter'},
        exec: function (editor) {
            editor.navigateLineEnd();
            editor.insert("\n");
        },
        readOnly: true
    });


    editor.commands.addCommand({
        name: 'ctrl-enter-1',
        bindKey: {win: 'Ctrl-Enter', mac: 'Command-Enter'},
        exec: function (editor) {
            editor.insert("\n");
        },
        readOnly: true
    });

    const origOnPaste = editor.onPaste;
    editor.onPaste = function (text) {
        console.log(text);
        if (text && curl.detectCURL(text)) {
            editor.handleCURLPaste(text);
            return;
        }
        origOnPaste.call(this, text);
    };

    editor.handleCURLPaste = function (text) {
        let curlInput = curl.parseCURL(text);
        editor.insert(curlInput);
    };

    editor.commands.addCommand({
        name: 'ctrl-shift-c',
        bindKey: {win: 'Ctrl-Shift-C', mac: 'Command-Shift-C'},
        exec: function (editor) {

            editor.focus();
            let text = editor.getSelectedText();
            if (!text) return;

            let matches = text.match(/^\s*(GET|POST|PUT|DELETE)[ |\t]+(.*)[\r\n]*([{\[][\s\S]*[}\]])?/);
            if (!matches) return;

            let id = "rapid-clipboard-textarea-hidden-id";
            let existsTextarea = document.getElementById(id);
            if (!existsTextarea) {
                let textarea = document.createElement("textarea");

                textarea.id = id;
                textarea.style.position = 'fixed';
                textarea.style.top = 0;
                textarea.style.left = 0;

                textarea.style.width = '1px';
                textarea.style.height = '1px';
                textarea.style.padding = 0;
                textarea.style.border = 'none';
                textarea.style.outline = 'none';
                textarea.style.boxShadow = 'none';

                textarea.style.background = 'transparent';
                document.querySelector("body").appendChild(textarea);
                existsTextarea = document.getElementById(id);
            }

            let verb = matches[1];
            let url = matches[2];
            let body = matches[3];

            if (verb) {
                let req = 'curl --unix-socket /var/run/docker.sock -X' + verb;
                if (url && url.length) {
                    req += ' \"http:/v1.27/' + encodeURI(url) + '"';
                }

                if (body && body.length) {
                    body = body.trim();
                    req += " -H \"Content-Type: application/json\"";
                    req += " -d'\n";
                    req += body.replace(/'/g, '\\"');
                    req += "'";
                }

                existsTextarea.value = req;
            }
            else {
                existsTextarea.value = text;
            }
            existsTextarea.select();

            try {
                let status = document.execCommand('copy');
                if (!status) {
                    console.error("Cannot copy text");
                } else {
                    console.log("The text is now on the clipboard");
                }
            } catch (err) {
                console.log('Unable to copy.');
            }
        },
        readOnly: true
    });

    editor.commands.addCommand({
        name: 'Alt-shift-up',
        bindKey: {win: 'Alt-Shift-Up', mac: 'Shift-Option-Up'},
        exec: function (editor) {
            editor.moveLinesUp();
        },
        readOnly: true
    });

    editor.commands.addCommand({
        name: 'Alt-shift-down',
        bindKey: {win: 'Alt-Shift-Down', mac: 'Shift-Option-Down'},
        exec: function (editor) {
            editor.moveLinesDown();
        },
        readOnly: true
    });

    editor.addEventListener("mousewheel", mouseWheelHandler);

    function mouseWheelHandler(event) {
        if (!event)
            return;
        event = window.event;

        if (event.ctrlKey && editor.getValue().length) {

            let fontSize = parseInt(editor.getFontSize());

            if (event.wheelDelta < 0 && fontSize > 8) {
                //mouse scroll down - min size 8
                editor.setFontSize((fontSize - 1) + "px");
            }
            else if (event.wheelDelta >= 0 && fontSize < 24) {
                //mouse scroll up - max size 24
                editor.setFontSize((fontSize + 1) + "px");
            }
        }
    }

};

/**
 * Created by hakan on 23/02/2017.
 */
import * as curl from './curl';

export const apply = (editor,ace) => {

    editor.commands.addCommand({
        name: 'Remove current line',
        bindKey: {win: 'Ctrl-X', mac: 'Command-X'},
        exec: function (editor) {

            editor.removeToLineEnd();
            editor.removeToLineStart();
        },
        readOnly: true
    });

    editor.commands.addCommand({
        name: 'Duplicate line',
        bindKey: {win: 'Ctrl-D', mac: 'Command-D'},
        exec: function (editor) {
            editor.copyLinesDown();
        },
        readOnly: true
    });

    editor.commands.addCommand({
        name: 'Start new line',
        bindKey: {win: 'Shift-Enter', mac: 'Shift-Enter'},
        exec: function (editor) {
            editor.navigateLineEnd();
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
        name: 'Copy the selection as cURL',
        bindKey: {win: 'Ctrl-Shift-C', mac: 'Command-Shift-C'},
        exec: function (editor) {

            editor.focus();
            let text = editor.getSelectedText();
            if (!text) return;

            let matches = text.match(/^\s*(GET|POST|PUT|DELETE)[ |\t]+(.*)[\r\n]*([{[][\s\S]*[}\]])?/);
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
                    req += ' "http:/v1.30/' + encodeURI(url) + '"';
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
        name: 'Move line up',
        bindKey: {win: 'Alt-Shift-Up', mac: 'Shift-Option-Up'},
        exec: function (editor) {
            editor.moveLinesUp();
        },
        readOnly: true
    });

    editor.commands.addCommand({
        name: 'Move line down',
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

            let fontSize = +(editor.getFontSize());

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

    editor.commands.addCommand({
        name: "show keyboard shortcuts",
        bindKey: {win: "Ctrl-Alt-h", mac: "Command-Alt-h"},
        exec: function(editor) {
            ace.config.loadModule("ace/ext/keybinding_menu", function(module) {
                module.init(editor);
                editor.showKeyboardShortcuts()
            })
        }
    });

    editor.commands.addCommand({
        name: "clear editor",
        bindKey: {win: "Ctrl-Shift-R", mac: "Command-Shift-R"},
        exec: function(editor) {
            editor.setValue("", 0);
        }
    });

    editor.commands.addCommand({
        name: "comment/uncomment with line comment",
        bindKey: {win: "Ctrl-/", mac: "Command-/"},
        exec: function(editor) {
            let cursorPosition = editor.getCursorPosition();
            cursorPosition.column = 0;

            let session = editor.getSession();
            let line = session.getLine(cursorPosition.row);
            let first = line[0] || "";

            if (first === "#")
                session.getDocument().removeInLine(cursorPosition.row, 0, 1);
            else
                session.insert(cursorPosition, "#");
        }
    });

};

/**
 * Created by hakan on 23/02/2017.
 */

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

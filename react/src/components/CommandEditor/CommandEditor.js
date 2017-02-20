/**
 * Created by hakan on 13/02/2017.
 */
import React from 'react'
import AceEditor from 'react-ace';

import 'brace/mode/javascript'
import 'brace/mode/json';
import 'brace/theme/github';

class CommandEditor extends React.Component {
    constructor(props) {
        super(props);
        this.state = {defaultValue: "GET containers/json"};
    }

    onSplitPaneChanged(newValue) {
        this.aceEditor.editor.session.setWrapLimitRange(0, newValue + 1);
        this.setState({defaultValue: this.aceEditor.editor.getValue()});
    }

    getSelectedText() {
        return this.aceEditor.editor.getSelectedText();
    }

    addCommandExample(command) {
        // askenkronluk sorun cikartiyor, kontrol gerekecek gibi
        this.aceEditor.editor.session.insert(this.aceEditor.editor.getCursorPosition(), command.example);
        this.aceEditor.editor.focus();
        this.setState({defaultValue: this.aceEditor.editor.getValue()});
    }

    componentDidMount() {
        this.aceEditor.editor.session.setUseWorker(false);
    }

    render() {
        return <AceEditor
            mode="javascript"
            theme="github"
            name="rest_editor"
            width="100%"
            height="100%"
            fontSize={14}
            tabSize={2}
            focus
            wrapEnabled
            enableBasicAutocompletion
            enableLiveAutocompletion
            highlightActiveLine
            showInvisibles
            editorProps={{$blockScrolling: Infinity}}
            value={this.state.defaultValue}
            ref={(input) => this.aceEditor = input}
        />
    }
}

export default CommandEditor;
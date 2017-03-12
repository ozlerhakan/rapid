/**
 * Created by hakan on 13/02/2017.
 */
import React from 'react'
import AceEditor from 'react-ace';
import * as shortcuts from './shortcuts';

import 'brace/mode/javascript'
import 'brace/theme/github';
import 'brace/ext/searchbox';

class CommandEditor extends React.Component {
    constructor(props) {
        super(props);

        let dValue = "// list all containers\n\nGET containers/json?all=true&size=true";
        const dashboard = localStorage.getItem("rapid-dashboard");
        if (dashboard) {
            dValue = dashboard;
        }
        this.state = {defaultValue: dValue};
        this.onChange = this.onChange.bind(this);
    }

    onSplitPaneChanged(newValue) {
        this.aceEditor.editor.session.setWrapLimitRange(0, newValue + 1);
        this.setState({defaultValue: this.aceEditor.editor.session.getValue()});
    }

    getSelectedText() {
        return this.aceEditor.editor.getSelectedText();
    }

    addCommandExample(command) {
        this.aceEditor.editor.session.insert(this.aceEditor.editor.getCursorPosition(), command.example);
        this.setState({defaultValue: this.aceEditor.editor.session.getValue()});

        this.aceEditor.editor.focus();
    }

    onChange() {
        this.setState({defaultValue: this.aceEditor.editor.session.getValue()});
    }

    componentDidMount() {
        this.aceEditor.editor.session.setUseWorker(false);
        shortcuts.apply(this.aceEditor.editor);
    }

    componentDidUpdate() {
        localStorage.setItem("rapid-dashboard", this.aceEditor.editor.session.getValue());
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
            onChange={this.onChange}
            editorProps={{$blockScrolling: Infinity}}
            value={this.state.defaultValue}
            ref={(input) => this.aceEditor = input}
        />
    }
}

export default CommandEditor;
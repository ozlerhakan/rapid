/**
 * Created by hakan on 13/02/2017.
 */
import React from 'react'
import AceEditor from 'react-ace';
import * as shortcuts from './shortcuts';
import * as docker from './docker-editor';

import 'brace/theme/chrome';
import 'brace/ext/searchbox';
import 'brace/ext/language_tools';
import 'brace/ext/keybinding_menu';
import 'brace/snippets/snippets';

class CommandEditor extends React.Component {
    constructor(props) {
        super(props);

        let dValue = "# list all containers\n\nGET containers/json?all=true&size=true";
        const dashboard = localStorage.getItem("rapid-dashboard");
        if (dashboard) {
            dValue = dashboard;
        }
        this.state = {defaultValue: dValue};
        this.onChange = this.onChange.bind(this);
        this.onBeforeLoad = this.onBeforeLoad.bind(this);
    }

    ace = null;

    onBeforeLoad(ace) {
        this.ace = ace;
        docker.init(ace);
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
        shortcuts.apply(this.aceEditor.editor,this.ace);
    }

    componentDidUpdate() {
        localStorage.setItem("rapid-dashboard", this.aceEditor.editor.session.getValue());
    }

    render() {
        return <AceEditor
            mode="docker"
            theme="chrome"
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
            enableSnippets
            onBeforeLoad={this.onBeforeLoad}
            onChange={this.onChange}
            editorProps={{$blockScrolling: Infinity}}
            value={this.state.defaultValue}
            ref={(input) => this.aceEditor = input}
        />
    }
}

export
default
CommandEditor;
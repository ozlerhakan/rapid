/**
 * Created by hakan on 12/02/2017.
 */
import React from 'react';
import SplitPane from 'react-split-pane';
import Header from './header';
import RightAceEditor from './rightAceEditor';
import TreeView from './TreeView';

import AceEditor from 'react-ace';

import 'brace/mode/json';
import 'brace/theme/github';

class HomePage extends React.Component {
    constructor(props) {
        super(props);

        this.onChangeSplitPane = this.onChangeSplitPane.bind(this);
        this.handlePlay = this.handlePlay.bind(this);

        this.state = {defaultValue: "GET v1.26/containers/json"};
    }

    onChangeSplitPane(newValue) {
        this.inLeftAceEditor.editor.session.setWrapLimitRange(0, newValue + 1);
        this.setState({defaultValue: this.inLeftAceEditor.editor.getValue()});
    }

    handlePlay() {
        const editor = this.inLeftAceEditor.editor;
        console.log(editor.getCursorPosition());
        const currline = editor.getSelectionRange().start.row;
        const wholelinetxt = editor.session.getLine(currline);

        console.log(wholelinetxt);
    }

    render() {

        return <div className="homePage">
            <Header onPlay={this.handlePlay}/>
            <SplitPane split="vertical" defaultSize="20%">
                <TreeView />
                <SplitPane split="vertical" onDragFinished={this.onChangeSplitPane} defaultSize="40%">
                    <AceEditor
                        mode="text"
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
                        ref={(input) => this.inLeftAceEditor = input}
                    />
                    <RightAceEditor/>
                </SplitPane>
            </SplitPane>
        </div>;
    }
}

export default HomePage
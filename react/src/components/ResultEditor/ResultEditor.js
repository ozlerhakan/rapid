/**
 * Created by hakan on 13/02/2017.
 */
import React from 'react'
import AceEditor from 'react-ace';

import 'brace/mode/json';
import 'brace/theme/chrome';

class ResultEditor extends React.Component {
    componentDidMount() {
        this.aceEditor.editor.session.setUseWorker(false);
    }

    onSplitPaneChanged(newValue) {
        let limit = this.aceEditor.editor.session.getWrapLimit();
        this.aceEditor.editor.session.setWrapLimitRange(0, (newValue - limit) + 1);
    }

    setResult(result) {
        if (typeof result === 'object') {
            result = JSON.stringify(result, null, 4);
        }
        this.aceEditor.editor.session.setValue(result, this.aceEditor.editor.getCursorPosition());
    }

    render() {
        return <AceEditor
            mode="json"
            theme="chrome"
            name="view_unique_json"
            width="100%"
            height="100%"
            readOnly
            wrapEnabled
            editorProps={{$blockScrolling: Infinity}}
            fontSize={14}
            ref={(input) => this.aceEditor = input}
        />
    }
}

export default ResultEditor;
/**
 * Created by hakan on 12/02/2017.
 */
import React from 'react';
import SplitPane from 'react-split-pane';
import 'whatwg-fetch';
import Header from '../Header';
import ResultEditor from '../ResultEditor';
import CommandEditor from '../CommandEditor';
import CommandTree from '../CommandTree';
import Style from './styles';
import loadingGif from '../../docker_loading.gif'

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {'loading': false};

        this.onChangeSplitPane = this.onChangeSplitPane.bind(this);
        this.handlePlay = this.handlePlay.bind(this);
        this.commandSelected = this.commandSelected.bind(this);
    }

    onChangeSplitPane(newValue) {
        this.commandEditor.onSplitPaneChanged(newValue);
        this.resultEditor.onSplitPaneChanged(newValue);
    }

    handlePlay() {
        let cmd = this.commandEditor.getSelectedText();

        let match = cmd.match(/^\s*(GET|POST|PUT|DELETE)[ |\t]+(.*)[\r\n]*([{[][\s\S]*[}\]])?/);
        if (!match) return;

        console.log('HTTP REQUEST: ' + match[1]);
        console.log('URL: ' + match[2]);
        console.log('DATA: ' + match[3]);

        this.sendRequest(match[1], match[2], match[3])
    }

    sendRequest(method, url, body) {
        let that = this;
        this.setState({'loading': true});
        fetch('/docker/' + url, {
            method,
            headers: {'Content-Type': 'application/json'},
            body: method === 'POST' ? body : null
        }).then(function (response) {
            return response.json();
        }).then(function (json) {
            that.setState({'loading': false});
            that.resultEditor.setResult(json);
        }).catch(function (ex) {
            that.setState({'loading': false});
            that.resultEditor.setResult(ex);
            console.log(ex);
        });
    }

    commandSelected(command) {
        this.commandEditor.addCommandExample(command);
    }

    render() {
        // <div style={Style.modal}><img src="/static/images/docker_loading.gif"/></div>
        return <div className="homePage">
            <Header onPlay={this.handlePlay}/>
            <SplitPane split="vertical" defaultSize="20%">
                <CommandTree commandSelected={this.commandSelected}/>
                <SplitPane split="vertical" onDragFinished={this.onChangeSplitPane} defaultSize="40%">
                    <CommandEditor ref={(i) => this.commandEditor = i}/>
                    <ResultEditor ref={(i) => this.resultEditor = i}/>
                </SplitPane>
            </SplitPane>
            {!!this.state.loading && <div style={Style.modal}>
                <img style={Style.dockerGif} src={loadingGif} alt="Loading..."/>
            </div>}
        </div>;
    }
}

export default HomePage
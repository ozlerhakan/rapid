/**
 * Created by hakan on 15/02/2017.
 */
import React from 'react';
import {Treebeard} from 'react-treebeard';
import FontAwesome from 'react-fontawesome';
import data from './data';
import * as filters from './filter';
import styles from './styles';

class TreeView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {data};
        this.onToggle = this.onToggle.bind(this);
    }

    onToggle(node, toggled) {
        if (this.state.cursor) {
            let temp = this.state.cursor;
            temp.active = false;
            this.setState({cursor:temp})
        }
        node.active = true;
        if (node.children) {
            node.toggled = toggled;
        }
        this.setState({cursor: node});

        if(node.example){
            this.props.commandSelected(node);
        }
    }

    onFilterMouseUp(e) {
        const filter = e.target.value.trim();
        if (!filter) {
            return this.setState({data});
        }
        let filtered = filters.filterTree(data, filter);
        filtered = filters.expandFilteredNodes(filtered, filter);
        this.setState({data: filtered});
    }

    render() {
        return (
            <div>
                <div style={styles.searchBox}>
                    <div className="input-group">
                        <span className="input-group-addon">
                          <FontAwesome name="search"/>
                        </span>
                        <input type="text"
                               className="form-control"
                               placeholder="Search..."
                               onKeyUp={this.onFilterMouseUp.bind(this)}
                        />
                    </div>
                </div>
                <Treebeard
                    data={this.state.data}
                    onToggle={this.onToggle}
                />
            </div>
        );
    }
}

export default TreeView;
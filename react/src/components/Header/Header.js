/**
 * Created by hakan on 12/02/2017.
 */
import React from 'react'
import {Nav,Navbar,NavItem,MenuItem,NavDropdown,Tooltip,OverlayTrigger} from 'react-bootstrap';
import FontAwesome from 'react-fontawesome';

class Header extends React.Component {

    constructor(props) {
        super(props);
        this.handleRun = this.handleRun.bind(this);
    }

    handleRun() {
        this.props.onPlay();
    }

    render() {
        let tooltip = (<Tooltip id="tooltip">Click to send the selected content</Tooltip>);
        return <Navbar>
            <Navbar.Header>
                <Navbar.Brand>
                    Rapid Dashboard
                </Navbar.Brand>
            </Navbar.Header>
            <Nav>
                <NavDropdown eventKey={1} title="API References" id="basic-nav-dropdown">
                    <MenuItem eventKey={1.1} href="https://docs.docker.com/engine/api/" target="_blank">Docker Remote
                        API</MenuItem>
                    <MenuItem divider/>
                    <MenuItem eventKey={1.2} href="https://docs.docker.com/engine/api/v1.30/"
                              target="_blank">v1.30</MenuItem>
                    <MenuItem divider/>
                    <MenuItem eventKey={1.3} href="https://docs.docker.com/engine/faq/" target="_blank">FAQ</MenuItem>
                </NavDropdown>
                <OverlayTrigger placement="right" overlay={tooltip}>
                    <NavItem eventKey={2} target="_blank" onClick={this.handleRun}>
                        <FontAwesome size='lg' name="play"/>
                    </NavItem>
                </OverlayTrigger>
            </Nav>
        </Navbar>;
    }
}

export default Header;
import {render } from 'react-dom';
import React, {Component} from 'react';
import {Router, Route, browserHistory} from 'react-router';
import { LoginComponent } from './components/LoginComponent';
import { UserComponent } from './components/UserComponent';
import { RegistrComponent } from './components/RegistrComponent';
import { UserlistComponent } from './components/UserlistComponent';
import { AddComponent } from './components/AddComponent';
import { EditComponent } from './components/EditComponent';

class App extends Component {
    render() {
        return (
                <Router history = {browserHistory}>
                    <Route path = "/" component = {LoginComponent} />
                    <Route path = "login" component = {LoginComponent} />
                    <Route path = "user" component = {UserComponent} />
                    <Route path = "registr" component = {RegistrComponent} />
                    <Route path = "userlist" component = {UserlistComponent}/>
                    <Route path = "add" component = {AddComponent} />
                    <Route path = "edit" component = {EditComponent} />
                </Router>
        );
    }
}

render(<App />, window.document.getElementById('root'));
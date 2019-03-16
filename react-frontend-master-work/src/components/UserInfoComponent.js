import React from "react";
import {browserHistory} from "react-router";

export class UserInfoComponent extends React.Component {
    constructor(props){
        super(props);
        this.removeData = this.removeData.bind(this);
    }

    removeData(){
        window.sessionStorage.removeItem('currentLogin');
        window.sessionStorage.removeItem('currentRole');
        window.sessionStorage.removeItem('bsc64Encoded');
        browserHistory.push('login');
    }

    render(){
        const login = window.sessionStorage.getItem('currentLogin');
        const role = window.sessionStorage.getItem('currentRole');
        return (
        <div>
            <p>{role}, {login}</p> 
            <button onClick={this.removeData}>Logout</button>
        </div>
        );
    }
}
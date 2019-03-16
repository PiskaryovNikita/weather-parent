import React from "react";
import { UserInfoComponent } from "./UserInfoComponent";
import {browserHistory} from "react-router";

export class UserComponent extends React.Component {
    constructor(props){
        super(props);
        this.check();
    }

    check(){
        if(!window.sessionStorage.getItem('currentLogin')) {
          alert('You should login first!');
          browserHistory.push('login');
        } else if(window.sessionStorage.getItem('currentRole') === 'ADMIN'){
          alert('You are not allowed to watch this page!');
          browserHistory.push('userlist');
        }
      }

    render(){
        return (<span>Hello, <UserInfoComponent /></span>);
    }
}
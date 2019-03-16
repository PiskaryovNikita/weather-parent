import React from "react";
import {Link} from "react-router";
import {browserHistory} from "react-router";

export class LoginComponent extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            login: '',
            password: '',
            isValid: true
        };
        this.handleLoginChange = this.handleLoginChange.bind(this);//this inside handleLoginChange is current obj
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.authenticate = this.authenticate.bind(this);
    }

    authenticate(event) {
        var authUrl = 'http://10.10.103.92:8080/webapi/userDetails';
        const bsc64Encoded = 'Basic ' + btoa(this.state.login + ':' + this.state.password);
        window.sessionStorage.setItem('bsc64Encoded', bsc64Encoded);
        fetch(authUrl, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Authorization': bsc64Encoded
            }}).then(res => res.json())
            .then(json => {
                if (json['login']) {
                    this.setState({isValid: true});
                    window.sessionStorage.setItem('currentLogin', json['login']);
                    window.sessionStorage.setItem('currentRole', json['role']['name']);
                    const roleName = json.role.name;
                    roleName === 'ADMIN' ? browserHistory.push("/userlist") : browserHistory.push("/user");
                } else{
                    this.setState({isValid: false});
                }
            });
        event.preventDefault();
    }

    handleLoginChange(event){
        this.setState({login: event.target.value});
    }

    handlePasswordChange(event){
        this.setState({password: event.target.value});
    }

    render(){
        var error = '';
        if(!this.state.isValid) {
            error = <p><font color="red">Invalid credentials</font></p>;
        }
        return (
        <div>
          <h2>Login </h2>
          <form onSubmit={this.authenticate}>
            <div>
              <label>Login:</label> 
              <input type="text" value={this.state.login} onChange={this.handleLoginChange} id="username" name="username"/>
            </div>
            <div>
              <label>Password:</label> 
              <input type="password" value={this.state.password} onChange={this.handlePasswordChange} id="password" name="password"/>
            </div>
            <input type="Submit" value="Submit" readOnly/>
            <Link to="registr">Registration</Link>
            {error}
          </form>
      </div>);
    }
}
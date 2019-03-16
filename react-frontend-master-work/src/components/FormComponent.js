import React, {Component} from 'react'
import {Link} from "react-router";
import {browserHistory} from "react-router";

export default class FormComponent extends Component {
    constructor(props){
        super(props);
        this.state = {
            users: [],
            processUri: 'http://10.10.103.92:8080',
            login: '',
            password: '',
            passwordAgain: '',
            firstName: '',
            lastName: '',
            email: '',
            birthday: '',
            role: {roleId: 1, name: 'User'},
            roles: [],
            errors: {}
        }
        if (props.formType !== 'edit') {
            this.getUsers();
        }
        if(props.formType === 'add' || props.formType === 'edit'){
            this.state.role = {roleId: '', name: ''};
            this.getRoles();
            if(props.formType === 'edit'){
                this.getUserById(window.sessionStorage.getItem('editId'));
            }
        } 
    }

    componentWillUnmount(){    
    }


    //no this inside method - not necs to bind
    check() {
        if(!window.sessionStorage.getItem('currentLogin')) {
          alert('You should login first!');
          browserHistory.push('login');
        } else if(window.sessionStorage.getItem('currentRole') === 'USER'){
          alert('You are not allowed to watch this page!');
          browserHistory.push('user');
        }
      }

    getRoles = () => {
        fetch(this.state.processUri + '/webapi/roles', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Authorization': window.sessionStorage.getItem('bsc64Encoded')
            }
        })  
            .then(res => res.json())
            .then(json => {
                this.setState({
                    roles: json,
                })
            });
    }

    getUserById = (id) => {
        fetch(this.state.processUri + '/webapi/users/' + id, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Authorization': window.sessionStorage.getItem('bsc64Encoded')
            }
        })    
            .then(res => res.json())
            .then(json => {
                this.setState({
                    userId: json.userId,
                    login: json.login,
                    password: '',
                    firstName: json.firstName,
                    lastName: json.lastName,
                    email: json.email,
                    birthday: json.birthday,
                    role: json.role
                })
            });
    }

    getUsers = () => {
        var headers = {
            'Accept': 'application/json',
            'Authorization': window.sessionStorage.getItem('bsc64Encoded')
        };
        if (this.props.formType === 'registr') {
            headers.Authorization = 'Basic bHN1eXVuOnF3ZXJ0eTEyMzQ1Njc4';
        }
        fetch(this.state.processUri + '/webapi/users', {
            method: 'GET',
            headers: headers
        })    
            .then(res => res.json())
            .then(json => {
                this.setState({
                    users: json,
                })
            });
    }

    render() {
        var roles = '';
        var login = <input type="text" value={this.state.login} onChange={this.loginChange}/>;
        if (this.props.formType === 'edit') {
            login = <input type="text" value={this.state.login} onChange={this.loginChange} />
        }
        if (this.props.roles) {
            roles = 
            <div>
                <label>roles:</label> 
                <select defaultValue={this.state.role.name} onChange={this.roleChange}>
                    {this.state.roles.map((role, key) =>
                    <option key={role.roleId} value={[role.roleId, role.name]}>{role.name}</option>
                    )}                    
                </select>
                <p><font color="red">{this.state.errors.role}</font></p>
            </div>;
        }
        return (
            <div className="component" align="center">
                {this.props.formType} user
                <form onSubmit={this.submit}>
                    <div>
                        <label>Login:</label> 
                        {login}
                        <p><font color="red">{this.state.errors.nonUniqueLogin}</font></p>
                        <p><font color="red">{this.state.errors.login}</font></p>
                    </div>
                    <div>
                        <label>Password:</label> 
                        <input type="password" value={this.state.password} onChange={this.passwordChange} />
                        <p><font color="red">{this.state.errors.password}</font></p>
                    </div>
                    <div>
                        <label>PasswordAgain:</label> 
                        <input type="password" value={this.state.passwordAgain} onChange={this.passwordAgainChange} />
                        <p><font color="red">{this.state.errors.passwordAgain}</font></p>
                    </div>
                    <div>
                        <label>firstName:</label> 
                        <input type="text" value={this.state.firstName} onChange={this.fnChange} />
                        <p><font color="red">{this.state.errors.firstName}</font></p>
                    </div>
                    <div>
                        <label>lastName:</label> 
                        <input type="text" value={this.state.lastName} onChange={this.lnChange} />
                        <p><font color="red">{this.state.errors.lastName}</font></p>
                    </div>
                    <div>
                        <label>email:</label> 
                        <input type="text" value={this.state.email} onChange={this.emailChange} />
                        <p><font color="red">{this.state.errors.nonUniqueEmail}</font></p>
                        <p><font color="red">{this.state.errors.email}</font></p>
                    </div>
                    <div>
                        <label>birthday:</label> 
                        <input type="date" min="1950-01-01" max="2014-12-31" value={this.state.birthday} onChange={this.birthdayChange} />
                        <p><font color="red">{this.state.errors.birthday}</font></p>
                    </div>
                    {roles}
                    <div>
                        <input type="Submit" value={this.props.formType} readOnly/>
                        <Link to={this.props.destination}>{this.props.destination}</Link>
                    </div>
                </form>
            </div>
        )
    }

    submit = (event) => {
        event.preventDefault();
        this.getUsers();
        if (!this.validate()) {
            alert('correct the form!')
            return;
        }
        var genericHeaders = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': window.sessionStorage.getItem('bsc64Encoded')
        };
        var genericBody = {
            login: this.state.login,
            password: this.state.password,
            email: this.state.email,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            birthday: this.state.birthday,
            role: this.state.role
        };
        if(this.props.formType === 'registr') {
            genericHeaders.Authorization = 'Basic bHN1eXVuOnF3ZXJ0eTEyMzQ1Njc4';
            fetch(this.state.processUri + '/webapi/users', {
                method: 'POST',
                headers: genericHeaders,
                body: JSON.stringify(genericBody)
            }).then(res => alert('Request delivered!'));
            browserHistory.push('login');
        } else if(this.props.formType === 'add') {
            fetch(this.state.processUri + '/webapi/users', {
                method: 'POST',
                headers: genericHeaders,
                body: JSON.stringify(genericBody)
            }).then(res => alert('Request delivered!'))
            browserHistory.push('userlist');
        } else if(this.props.formType === 'edit') {
            fetch(this.state.processUri + '/webapi/users/' + this.state.userId, {
                method: 'PUT',
                headers: genericHeaders,
                body: JSON.stringify(genericBody)
            }).then(res => alert('Request delivered!'));
            browserHistory.push('userlist');
        }
    }

    loginChange = (event) => {
        this.setState({login: event.target.value});
    }

    passwordChange = (event) => {
        this.setState({password: event.target.value});
    }

    passwordAgainChange = (event) => {
        this.setState({passwordAgain: event.target.value});
    }

    fnChange = (event) => {
        this.setState({firstName: event.target.value});
    }

    lnChange = (event) => {
        this.setState({lastName: event.target.value});
    }

    emailChange = (event) => {
        this.setState({email: event.target.value});
    }

    birthdayChange = (event) => {
        this.setState({birthday: event.target.value});
    }

    roleChange = (event) => {
        let s = event.target.value;
        let roleId = +s.substr(0, s.indexOf(','));
        let name = s.substr(s.indexOf(',') + 1, s.length);
        this.setState({role: {roleId: roleId, name: name}});
    }

    validate = () => {
        let errors = {};
        let formIsValid = true;
        this.state.users.forEach(user => {
            if(this.props.formType !== 'edit' && this.state.login === user.login) {
                formIsValid = false;
                errors.nonUniqueLogin = "Your login isn't unique!";
            }
            if (this.props.formType !== 'edit' && this.state.email === user.email) {
                formIsValid = false;
                errors.nonUniqueEmail = "Your login isn't unique!";
            }
        });
        if (!this.state.login.match(/^[A-Za-z][A-Za-z\d]{6,19}$/)) {
            formIsValid = false;
            errors.login = 'Login should match pattern ^[A-Za-z][A-Za-zd]{6,19}$';
        }
        if (!this.state.password.match(/^[A-Z][A-Za-z\d]{6,19}$/)) {
            formIsValid = false;
            errors.password = 'Password should match pattern ^[A-Z][A-Za-zd]{6,19}$';
        }
        if (this.state.passwordAgain !== this.state.password) {
            formIsValid = false;
            errors.passwordAgain = 'Passwords doesnt match!';
        }
        if (!this.state.firstName.match(/^[A-Z][A-Za-z]{6,19}$/)) {
            formIsValid = false;
            errors.firstName = 'Firstname should match pattern ^[A-Z][A-Za-z]{6,19}$';
        }
        if (!this.state.lastName.match(/^[A-Z][A-Za-z]{6,19}$/)) {
            formIsValid = false;
            errors.lastName = 'Lastname should match pattern ^[A-Z][A-Za-z]{6,19}$';
        }
        if(!this.state.email.match(/^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/)) {
            formIsValid = false;
            errors.email = "Email should match pattern /^w+@[a-zA-Z_]+?.[a-zA-Z]{2,3}$/"
        }
        if (this.state.birthday === '' || this.state.birthday.length !== 10) {
            formIsValid = false;
            errors.birthday = "You should set the birthday, mm-dd-yyyy"
        }
        if (this.props.formType !== 'registr' && this.state.roleId === '') {
            formIsValid = false;
            errors.role = 'You should choose role';
        }
        this.setState({errors: errors});
        return formIsValid;
    }
}
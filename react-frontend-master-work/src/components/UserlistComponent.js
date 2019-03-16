import React, {Component} from 'react'
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import CircularProgress from '@material-ui/core/CircularProgress';
import { UserInfoComponent } from './UserInfoComponent';
import {Link} from "react-router";
import {browserHistory} from "react-router";

export class UserlistComponent extends Component {
    constructor(props) {
        super(props);
        this.check();
        this.state = {
            processUri: 'http://10.10.103.92:8080',
            users: [],
            isLoaded: false,
        }
    }

    check(){
        if(!window.sessionStorage.getItem('currentLogin')) {
          alert('You should login first!');
          browserHistory.push('login');
        } else if(window.sessionStorage.getItem('currentRole') === 'USER'){
          alert('You are not allowed to watch this page!');
          browserHistory.push('user');
        }
      }

    componentDidMount = () => {
        fetch(this.state.processUri + '/webapi/users', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Authorization': window.sessionStorage.getItem('bsc64Encoded')
            }
        })
            .then(res => res.json())
            .then(json => {
                console.log(json)
                this.setState({
                    users: json,
                    isLoaded: true
                })
            });
        this.setState({isLoaded: false});
    }

    render() {
        if (this.state.isLoaded) {
            return (
                <Paper>
                    <UserInfoComponent/>
                    <Link to="add">Add user</Link>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>userId</TableCell>
                                <TableCell>Login</TableCell>
                                <TableCell>Email</TableCell>
                                <TableCell>First name</TableCell>
                                <TableCell>Last name</TableCell>
                                <TableCell>Age</TableCell>
                                <TableCell>Role</TableCell>
                                <TableCell>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.users.map((user, key) =>
                                <TableRow key={key} className="table">
                                    <TableCell>{user.userId}</TableCell> 
                                    <TableCell> {user.login}</TableCell>
                                    <TableCell> {user.email}</TableCell>
                                    <TableCell> {user.firstName}</TableCell>
                                    <TableCell> {user.lastName}</TableCell>
                                    <TableCell> {this.calculate_age(user.birthday)}</TableCell>
                                    <TableCell> {user.role.name}</TableCell>
                                    <TableCell>
                                        <button onClick={this.editUser.bind(this, user.userId)}>Edit</button>
                                        <button onClick={this.deleteUser.bind(this, user.userId)}>Delete</button>
                                    </TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </Paper>
            )
        } else {
            return (
                <div align="center">
                    <CircularProgress/>
                </div>
            )
        }
    }

    calculate_age(dateJson) {
        let date = new Date(dateJson);
        let today_date = new Date();
        let today_year = today_date.getFullYear();
        let today_month = today_date.getMonth();
        let today_day = today_date.getDate();
        let age = today_year - date.getFullYear();
        if (today_month < (date.getMonth() - 1)) {
            age--;
        } else if (((today_month === date.getMonth() - 1)) && (today_day < date.getDay())) {
            age--;
        }
        return age;
    }

    editUser(id) {
        window.sessionStorage.setItem('editId', id);
        browserHistory.push('edit');
    }

    deleteUser(id) {
        const result = window.confirm('Are you sure, you want to delete this user?');
        if(result){
            this.setState({isLoaded: false});
            fetch(this.state.processUri + '/webapi/users/' + id, {
                    method: 'DELETE',
                    headers: {
                        'Accept': 'application/json',
                        'Authorization': window.sessionStorage.getItem('bsc64Encoded')
                    },
                }
            ).then(this.componentDidMount())
        }
    }
}
DROP TABLE IF EXISTS roles;
CREATE TABLE roles(role_id LONG auto_increment PRIMARY KEY, name VARCHAR(255));
INSERT INTO roles(name) VALUES('user');
INSERT INTO roles(name) VALUES('admin');

DROP TABLE IF EXISTS users;
CREATE TABLE users(user_id LONG auto_increment PRIMARY KEY, login VARCHAR(255), 
password VARCHAR(255), email VARCHAR(255), 
firstName VARCHAR(255), lastName VARCHAR(255), 
birthday DATE, role_id LONG,
foreign key (role_id) references roles(role_id));
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

INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('petrovpetya', 'petya123', 'petya@gmail.com', 
'Petya', 'Petrov', '1998-01-01', 1);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('vasya', 'vasiliiPetrovich', 'vasilii17@gmail.com', 
'Vasilii', 'Oprishkin', '1998-02-02', 1);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('kostya19', 'qwerty', 'nkostya1997@gmail.com', 
'Kostya', 'Aleekseenko', '1999-07-07', 2);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('nikita', 'petya123', 'petya@gmail.com', 
'Nikita', 'Piskaryov', '1998-01-01', 2);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('lsuyun', 'vasiliiPetrovich', 'vasilii17@gmail.com', 
'Suyun', 'Liang', '1998-02-02', 2);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('jenya', 'qwerty', 'nkostya1997@gmail.com', 
'Kostya', 'Aleekseenko', '1999-07-07', 2);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('pasha', 'petya123', 'petya@gmail.com', 
'Petya', 'Petrov', '1998-01-01', 1);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('jeka', 'vasiliiPetrovich', 'vasilii17@gmail.com', 
'Vasilii', 'Oprishkin', '1998-02-02', 1);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('vlad', 'qwerty', 'nkostya1997@gmail.com', 
'Kostya', 'Aleekseenko', '1999-07-07', 2);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('zxc', 'petya123', 'petya@gmail.com', 
'Petya', 'Petrov', '1998-01-01', 1);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('qwe', 'vasiliiPetrovich', 'vasilii17@gmail.com', 
'Vasilii', 'Oprishkin', '1998-02-02', 1);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('asd', 'qwerty', 'nkostya1997@gmail.com', 
'Kostya', 'Aleekseenko', '1999-07-07', 2);

SELECT * FROM (users INNER JOIN roles ON users.role_id = roles.role_id);

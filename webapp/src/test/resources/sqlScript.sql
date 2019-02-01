DROP TABLE IF EXISTS roles;
CREATE TABLE roles(role_id LONG auto_increment PRIMARY KEY, name VARCHAR(255));

INSERT INTO roles(name) VALUES('guest');
INSERT INTO roles(name) VALUES('authorized');
INSERT INTO roles(name) VALUES('admin');

DROP TABLE IF EXISTS users;
CREATE TABLE users(user_id LONG auto_increment PRIMARY KEY, login VARCHAR(255), 
password VARCHAR(255), email VARCHAR(255), 
firstName VARCHAR(255), lastName VARCHAR(255), 
birthday DATE, role_id LONG,
foreign key (role_id) references roles(role_id));

INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('petrovpetya', 'petya123', 'petya@gmail.com', 
'Petya', 'Petrov', '1998-01-01', 2);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('vasilii1995', 'vasiliiPetrovich', 'vasilii17@gmail.com', 
'Vasilii', 'Oprishkin', '1998-02-02', 1);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('kostya19', 'qwerty', 'nkostya1997@gmail.com', 
'Kostya', 'Aleekseenko', '1999-07-07', 3);
INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES('kostya19', 'qwerty', 'nkostya1997@gmail.com', 
'Kostya', 'Aleekseenko', '1999-07-07', 3);

SELECT * FROM (users INNER JOIN roles ON users.role_id = roles.role_id);

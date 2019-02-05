package com.nixsolutions.webapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.nixsolutions.webapp.dao.tables.User;

public interface UserDao {
	void create(User user) throws SQLException;
	void update(User user) throws SQLException;
	void remove(User user) throws SQLException;
	List<User> findAll() throws SQLException;
	User findByLogin(String login) throws SQLException;
	User findByEmail(String email) throws SQLException;
}

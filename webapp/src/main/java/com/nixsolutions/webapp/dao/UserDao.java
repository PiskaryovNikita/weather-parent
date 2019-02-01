package com.nixsolutions.webapp.dao;

import java.util.List;

import com.nixsolutions.webapp.dao.tables.User;

public interface UserDao {
	void create(User user);
	void update(User user);
	void remove(User user);
	List<User> findAll();
	User findByLogin(String login);
	User findByEmail(String email);
}

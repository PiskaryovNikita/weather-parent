package com.nixsolutions.webapp.persistence;

import java.util.List;

import com.nixsolutions.webapp.model.User;

public interface UserDao {
	void create(User user);
	void update(User user);
	void remove(User user);
	List<User> findAll();
	User findByLogin(String login);
	User findByEmail(String email);
	User findById(Long userId);
}

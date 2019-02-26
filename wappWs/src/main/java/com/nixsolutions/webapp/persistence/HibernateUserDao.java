package com.nixsolutions.webapp.persistence;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.webapp.dao.UserDao;
import com.nixsolutions.webapp.modelClasses.User;

@Repository
public class HibernateUserDao extends HibernateDao implements UserDao {
	@Override
	@Transactional
	public void create(User user) {
		Objects.requireNonNull(user);
		createObject(user);
	}

	@Override
	@Transactional
	public User findByEmail(String email) {
		Objects.requireNonNull(email);
		// syntax for select
		String hql = "FROM User where email = :search_factor";
		return (User) findObject(hql, email);
	}

	@Override
	@Transactional
	public User findByLogin(String login) {
		Objects.requireNonNull(login);
		String hql = "FROM User where login = :search_factor";
		return (User) findObject(hql, login);
	}

	@Override
	@Transactional
	public User findById(Long id) {
		Objects.requireNonNull(id);
		String hql = "FROM User where userId = :search_factor";
		return (User) findObject(hql, id);
	}

	@Override
	@Transactional
	public List<User> findAll() {
		String hql = "FROM User";
		return findList(hql);
	}

	@Override
	@Transactional
	public void update(User user) {
		Objects.requireNonNull(user);
		if (findById(user.getUserId()) == null) {
			throw new RuntimeException(user.toString() + "doesn't exist");
		}
		updateObject(user);
	}

	@Override
	@Transactional
	public void remove(User user) {
		Objects.requireNonNull(user);
		removeObject(user);
	}

	@Transactional
	public void removeAll() {
		removeAllObjects("DELETE User");
	}
}

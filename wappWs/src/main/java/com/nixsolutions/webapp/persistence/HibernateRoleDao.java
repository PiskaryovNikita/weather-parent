package com.nixsolutions.webapp.persistence;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.webapp.dao.RoleDao;
import com.nixsolutions.webapp.modelClasses.Role;

@Repository
public class HibernateRoleDao extends HibernateDao implements RoleDao {

	@Override
	@Transactional
	public void create(Role role) {
		Objects.requireNonNull(role);
		createObject(role);
	}

	@Override
	@Transactional
	public void update(Role role) {
		Objects.requireNonNull(role);
		if (findByName(role.getName()) != null) {
			throw new RuntimeException(role.toString() + "entry with this name already in db");
		}
		updateObject(role);
	}

	@Override
	@Transactional
	public void remove(Role role) {
		Objects.requireNonNull(role);
		if (findByName(role.getName()) == null) {
			throw new RuntimeException(role.toString() + "doesn't exist in DB");
		}
		removeObject(role);
	}

	@Transactional
	public void removeAll() {
		removeAllObjects("DELETE Role");
	}

	@Override
	@Transactional
	public Role findByName(String name) {
		Objects.requireNonNull(name);
		String hql = "FROM Role R WHERE R.name = :search_factor";
		Role result = (Role) findObject(hql, name);
		return result;
	}

	@Transactional
	public List<Role> findAll() {
		String hql = "FROM Role";
		return findList(hql);
	}
}

package com.nixsolutions.webapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.nixsolutions.webapp.modelClasses.Role;

public interface RoleDao {
	void create(Role role) throws SQLException;

	void update(Role role) throws SQLException;

	void remove(Role role) throws SQLException;

	Role findByName(String name) throws SQLException;

	List<Role> findAll() throws SQLException;
}

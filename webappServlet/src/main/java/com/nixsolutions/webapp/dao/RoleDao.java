package com.nixsolutions.webapp.dao;

import java.sql.SQLException;

import com.nixsolutions.webapp.modelClasses.Role;

public interface RoleDao {
	void create(Role role) throws SQLException;
	void update(Role role) throws SQLException;
	void remove(Role role) throws SQLException;
	Role findByName(String name) throws SQLException;
}

package com.nixsolutions.webapp.dao;

import com.nixsolutions.webapp.dao.tables.Role;

public interface RoleDao {
	void create(Role role);
	void update(Role role);
	void remove(Role role);
	Role findByName(String name);
}

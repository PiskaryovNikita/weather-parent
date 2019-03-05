package com.nixsolutions.webapp.service;

import java.util.List;

import com.nixsolutions.webapp.model.Role;

public interface RoleService {
	void create(Role role);
	void update(Role role);
	void remove(Role role);
	Role findById(Long roleId);
	Role findByName(String name);
	List<Role> findAll();
}

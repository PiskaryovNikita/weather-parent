package com.nixsolutions.soapClTest;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import user.wssoap.nixsolutions.com.Role;
import user.wssoap.nixsolutions.com.User;
import user.wssoap.nixsolutions.com.UserService;
import user.wssoap.nixsolutions.com.UserSoap;

//Date - problem
@SuppressWarnings("deprecation")
public class WsTest {
	private UserService userService = new UserService();
	private UserSoap sei;
	private static User user;

	@BeforeClass
	public static void init() {
		Role role = new Role();
		role.setRoleId(1l);
		role.setName("User");
		user = new User();
		user.setUserId(1l);
		user.setLogin("xyzLogin");
		user.setPassword("qwerty");
		user.setEmail("xyzEmail");
		user.setFirstName("xyzfname");
		user.setLastName("xyzlname");
		user.setRole(role);
	}
	
	@Before
	public void setUp() throws Exception {
		sei = userService.getUserSoapImplPort();
	}

	@Test
	public void getUsers() {
		assertNotNull(sei.findAllUsers());
	}

	@Test
	public void getUserById() {
		assertNotNull(sei.findUserById(1l));
	}
	
	@Test
	public void getUserByLogin() {
		assertNotNull(sei.findUserByLogin("login1"));
	}
	
	@Test
	public void getUserEmail() {
		assertNotNull(sei.findUserByEmail("email1"));
	}

	@Test
	public void createUser() {
		sei.createUser(user);
		assertNotNull(sei.findUserByLogin(user.getLogin()));
	}

	@Test
	public void removeUser() {
		sei.removeUser(user);
		assertNull(sei.findUserByLogin(user.getLogin()));
	}

	//need some existing id
	@Test
	public void updateUser() {
		
	}

}

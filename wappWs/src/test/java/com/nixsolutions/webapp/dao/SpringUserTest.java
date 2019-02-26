package com.nixsolutions.webapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nixsolutions.webapp.dao.UserDao;
import com.nixsolutions.webapp.modelClasses.Role;
import com.nixsolutions.webapp.modelClasses.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("/userDataset/USER.xml")
@DatabaseTearDown(type = com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL, value = "/userDataset/USER.xml")
@DbUnitConfiguration(databaseConnection = "dataSource")
public class SpringUserTest {
	@Autowired
	private UserDao userDao;

	@Test
	@ExpectedDatabase(value = "/userDataset/expDsCreate.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreate() throws Exception {
		User userCr = new User("l", "s", "sl", "s", "l", Date.valueOf("1999-07-07"), new Role(2l, "admin"));
		userDao.create(userCr);
	}

	@Test
	@ExpectedDatabase(value = "/userDataset/expDsRemove.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testRemove() throws Exception {
		User userDel = new User(30l, "jdjra", "s", "sl", "s", "l", Date.valueOf("1999-07-07"), new Role(1l, "user"));
		userDao.remove(userDel);
	}

	@Test
	@ExpectedDatabase(value = "/userDataset/expDsUpd.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdate() throws Exception {
		User userUpd = new User(30l, "jdjra", "pass", "djuraemail", "fn", "ln", Date.valueOf("1999-07-07"),
				new Role(2l, "admin"));
		userDao.update(userUpd);
	}

	@Test
	public void testFindByEmail() throws Exception {
		assertNotNull(userDao.findByEmail("lsuyun123"));
	}

	@Test
	public void testFindByLogin() throws Exception {
		assertNotNull(userDao.findByLogin("liangsuyun"));
	}

	@Test
	public void testFindAll() throws Exception {
		List<User> users = userDao.findAll();
		List<User> exp = new ArrayList<>();
		exp.addAll(Arrays.asList(
				new User(10l, "liangsuyun", "qwerty", "lsuyun123", "Suyun", "Liang", Date.valueOf("1998-08-06"),
						new Role(2l, "admin")),
				new User(20l, "npiskaryov", "12345", "nikita321", "Nikita", "Piskaryov", Date.valueOf("1999-01-15"),
						new Role(2l, "admin")),
				new User(30l, "jdjra", "zxc", "edjura987", "Eugene", "Djura", Date.valueOf("1999-07-07"),
						new Role(1l, "user"))));
		assertNotNull(users);
		assertEquals(exp, users);
	}

	@Test(expected = RuntimeException.class)
	public void testFkViol() throws Exception {
		userDao.create(new User(4l, "jdjra", "s", "sl", "s", "l", new Date(0), new Role(777l, "xxx")));
	}

	@Test(expected = RuntimeException.class)
	public void testUpdUnexist() throws Exception {
		userDao.update(new User(1l, "liangsuyunx123", "qwerty", "lsuyun123", "Suyun", "Liang",
				Date.valueOf("1998-08-06"), new Role(2l, "admin")));
	}

	@Test(expected = RuntimeException.class)
	public void testDelUnexisted() throws Exception {
		userDao.remove(new User(4l, "zzz", "s", "sl", "s", "l", new Date(0), new Role(2l, "admin")));
	}
	
	@After
	public void afterAll() throws SQLException {
		System.out.println(userDao.findAll());
	}
	
}

package com.nixsolutions.webapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.nixsolutions.webapp.dao.RoleDao;
import com.nixsolutions.webapp.modelClasses.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("/roleDataset/ROLE.xml")
@DatabaseTearDown(type=com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL, value="/roleDataset/ROLE.xml")
@DbUnitConfiguration(databaseConnection="dataSource")
public class SpringRoleTest {
	@Autowired
	private RoleDao roleDao;

	@Test
	@ExpectedDatabase(value="/roleDataset/expCreate.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void testCreate() throws Exception {
		roleDao.create(new Role(3l, "x"));
	}

	@Test
	@ExpectedDatabase(value="/roleDataset/expDel.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void testRemove() throws Exception {
		roleDao.remove(new Role(2l, "admin"));
	}

	@Test
	@ExpectedDatabase(value="/roleDataset/expUpd.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void testUpdate() throws Exception {
		roleDao.update(new Role(1l, "m"));
	}

	@Test
	public void testFind() throws Exception {
		Role role = roleDao.findByName("admin");
		assertNotNull(role);
		assertEquals(2l, (long) role.getRoleId());
	}

	@Test(expected = RuntimeException.class)
	public void testPkViol() throws Exception {
		roleDao.create(new Role(1l, ""));
	}

	@Test(expected = RuntimeException.class)
	public void testUpdToExsited() throws Exception {
		roleDao.update(new Role(1l, "user"));
	}

	@Test(expected = RuntimeException.class)
	public void testDelUnexisted() throws Exception {
		roleDao.remove(new Role(6l, "xxx"));
	}
}

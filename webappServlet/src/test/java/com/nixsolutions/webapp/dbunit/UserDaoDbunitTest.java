package com.nixsolutions.webapp.dbunit;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.Test;

import com.nixsolutions.webapp.dao.AbstractJdbcDao;
import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.modelClasses.Role;
import com.nixsolutions.webapp.modelClasses.User;

public class UserDaoDbunitTest extends DBTestCase {
	private static final String H2URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
	private JdbcUserDao userDao = new JdbcUserDao();

	public void createTables() throws Exception {
		Connection connection = null;
		AbstractJdbcDao.setH2URL(H2URL);
		connection = DriverManager.getConnection(H2URL, "sa", "");
		RunScript.execute(connection, new FileReader(UserDaoDbunitTest.class.getResource("/schemaUsers.sql").getPath()));
		connection.close();
	}

	public UserDaoDbunitTest(String name) throws Exception {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, H2URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
	}
	
	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {
		super.setUpDatabaseConfig(config);
		try {
			createTables();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.INSERT;
	}

	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		String filePath = this.getClass().getResource("/users.xml").getPath();
		return new FlatXmlDataSetBuilder().build(new FileInputStream(filePath));
	}

	@Test
	public void testCreate() throws Exception {
		User userCr = new User(4l, "l", "s", "sl", "s", "l", new Date(0), new Role(1l, "admin"));
		userDao.create(userCr);
		User user = userDao.findByLogin(userCr.getLogin());
		assertNotNull(user);
		assertEquals(userCr.getFirstName(), user.getFirstName());
	}

	@Test
	public void testRemove() throws Exception {
		User userDel = new User(4l, "jdjra", "s", "sl", "s", "l", new Date(0), new Role(1l, "admin"));
		userDao.remove(userDel);
		assertNull(userDao.findByLogin("jdjra"));
	}

	@Test
	public void testUpdate() throws Exception {
		User userUpd = new User(4l, "jdjra", "s", "sl", "s", "l", new Date(0), new Role(1l, "admin"));
		userDao.update(userUpd);
		User user = userDao.findByEmail(userUpd.getEmail());
		assertNotNull(user);
		assertEquals(userUpd.getPassword(), user.getPassword());
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
		assertNotNull(users);
	}
}

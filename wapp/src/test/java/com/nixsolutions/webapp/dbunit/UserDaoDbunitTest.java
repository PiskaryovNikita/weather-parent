package com.nixsolutions.webapp.dbunit;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;

import com.nixsolutions.webapp.dao.AbstractJdbcDao;
import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.dao.tables.Role;
import com.nixsolutions.webapp.dao.tables.User;

public class UserDaoDbunitTest extends DBTestCase {
	private static final String H2URL = "jdbc:h2:mem:testdb";
	private JdbcUserDao userDao = new JdbcUserDao();
	
	static {
		createTables();
	}

	private static void createTables() {
		Connection connection = null;
		try {
			JdbcUserDao dao = new JdbcUserDao();
			AbstractJdbcDao.setH2URL(H2URL);
			connection = dao.createConnection();
			RunScript.execute(connection, new FileReader(UserDaoDbunitTest.class.getResource("/schemaUsers.sql").getPath()));
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public UserDaoDbunitTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, H2URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
		// System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		String filePath = this.getClass().getResource("/users.xml").getPath();
		return new FlatXmlDataSetBuilder().build(new FileInputStream(filePath));
	}

	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.REFRESH;
	}

	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.NONE;
	}

	@Before
	public void prepare() {
		Connection connection = null;
		try {
			connection = userDao.createConnection();
			DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(connection), getDataSet());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testCreate() {
		try {
			User userCr = new User(4l, "l", "s", "sl", "s", "l", new Date(0), new Role(1l, "admin"));
			userDao.create(userCr);
			User user = userDao.findByLogin(userCr.getLogin());
			assertNotNull(user);
			assertEquals(userCr.getFirstName(), user.getFirstName());
		} catch (Exception e) {
		}
	}

	@Test
	public void testRemove() {
		try {
			User userDel = new User(4l, "jdjra", "s", "sl", "s", "l", new Date(0), new Role(1l, "admin"));
			userDao.remove(userDel);
			assertNull(userDao.findByLogin("jdjra"));
		} catch (Exception e) {
			System.err.println("at testRemove: " + e);
		} 
	}

	@Test
	public void testUpdate() {
		try {
			User userUpd = new User(4l, "jdjra", "s", "sl", "s", "l", new Date(0), new Role(1l, "admin"));
			userDao.update(userUpd);
			User user = userDao.findByEmail(userUpd.getEmail());
			assertNotNull(user);
			assertEquals(userUpd.getPassword(), user.getPassword());
		} catch (Exception e) {
			System.err.println("at testUpdate: " + e);
		} 
	}

	@Test
	public void testFindByEmail() {
		try {
			assertNotNull(userDao.findByEmail("lsuyun123"));
		} catch (Exception e) {
			System.err.println("at testFindByEmail: " + e);
		} 
	}

	@Test
	public void testFindByLogin() {
		try {
			assertNotNull(userDao.findByLogin("liangsuyun"));
		} catch (Exception e) {
			System.err.println("at testFindByLogin: " + e);
		} 
	}

	@Test
	public void testFindAll() {
		try {
			List<User> users = userDao.findAll();
			assertNotNull(users);
			assertTrue(users.size() == 3);
		} catch (Exception e) {
			System.err.println("at testFindAll: " + e);
		} 
	}
}

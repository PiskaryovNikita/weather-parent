package com.nixsolutions.webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.sql.Date;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.Test;

import com.nixsolutions.webapp.dao.AbstractJdbcDao;
import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.dao.tables.Role;
import com.nixsolutions.webapp.dao.tables.User;

public class UserDaoTestMock {
	private static final String H2URL = "jdbc:h2:mem:testdb";
	private User u1 = new User(1L, "liangsuyun", "123", "jenya@mail.ru", "jenya", "surname1",
			new Date(System.currentTimeMillis()), new Role(1l, "guest"));
	private User u1upd = new User(1L, "liangsuyun", "789", "liang@123.ru", "su", "yun",
			new Date(System.currentTimeMillis()), new Role(1l, "guest"));
	private User u2 = new User(2L, "nikita", "1234", "nikita@list.ru", "nikita", "surname2",
			new Date(System.currentTimeMillis()), new Role(2l, "authorized"));
	private List<User> usrsExpected = Arrays.asList(u1, u2);
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
			RunScript.execute(connection, new FileReader(UserDaoTestMock.class.getResource("schemaUsers.sql").getPath()));
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

	private void cleanUsersTable() {
		Connection connection = null;
		try {
			connection = userDao.createConnection();
			connection.createStatement().execute("DELETE from users;");
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void insertEntries() {
		try {
			userDao.create(u1);
			userDao.create(u2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public synchronized void testCreate() {
		try {
			insertEntries();
			User user = userDao.findByLogin(u1.getLogin());
			assertNotNull(user);
			assertEquals(u1.getFirstName(), user.getFirstName());
		} catch (Exception e) {
		} finally {
			cleanUsersTable();
		}
	}

	@Test
	public synchronized void testRemove() {
		try {
			insertEntries();
			userDao.remove(u1);
			assertNull(userDao.findByLogin(u1.getLogin()));
		} catch (Exception e) {
			System.err.println("at testRemove: " + e);
		} finally {
			cleanUsersTable();
		}
	}

	@Test
	public synchronized void testUpdate() {
		try {
			insertEntries();
			userDao.update(u1upd);
			User user = userDao.findByEmail(u1upd.getEmail());
			assertNotNull(user);
			assertEquals(u1upd.getLogin(), user.getLogin());
		} catch (Exception e) {
			System.err.println("at testUpdate: " + e);
		} finally {
			cleanUsersTable();
		}
	}

	@Test
	public synchronized void testFindByEmail() {
		try {
			insertEntries();
			assertNotNull(userDao.findByEmail(u2.getEmail()));
		} catch (Exception e) {
			System.err.println("at testFindByEmail: " + e);
		} finally {
			cleanUsersTable();
		}
	}

	@Test
	public synchronized void testFindByLogin() {
		try {
			insertEntries();
			assertNotNull(userDao.findByLogin(u2.getLogin()));
		} catch (Exception e) {
			System.err.println("at testFindByLogin: " + e);
		} finally {
			cleanUsersTable();
		}
	}

	@Test
	public synchronized void testFindAll() {
		try {
			insertEntries();
			List<User> users = userDao.findAll();
			assertNotNull(users);
			assertTrue(users.size() == usrsExpected.size());
		} catch (Exception e) {
			System.err.println("at testFindAll: " + e);
		} finally {
			cleanUsersTable();
		}
	}
}

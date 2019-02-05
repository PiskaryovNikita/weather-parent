package com.nixsolutions.webapp;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.Test;

import com.nixsolutions.webapp.dao.AbstractJdbcDao;
import com.nixsolutions.webapp.dao.JdbcRoleDao;
import com.nixsolutions.webapp.dao.tables.Role;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RoleDaoTestMock {
	// to keep db opened
	// jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
	private static final String H2URL = "jdbc:h2:mem:testdb";
	private JdbcRoleDao roleDao = new JdbcRoleDao();

	static {
		createTable();
	}

	private static void createTable() {
		Connection connection = null;
		try {
			JdbcRoleDao dao = new JdbcRoleDao();
			AbstractJdbcDao.setH2URL(H2URL);
			connection = dao.createConnection();
			RunScript.execute(connection, new FileReader(RoleDaoTestMock.class.getResource("schemaRoles.sql").getPath()));
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void insertEntries() {
		try {
			roleDao.create(new Role(1l, "guest"));
			roleDao.create(new Role(2l, "authorized"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeEntries() {
		Connection connection = null;
		try {
			connection = roleDao.createConnection();
			connection.setAutoCommit(false);
			connection.createStatement().execute("DELETE FROM roles;");
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
	
	@Test
	public synchronized void testCreate() {
		insertEntries();
		try {
			Role role = roleDao.findByName("guest");
			assertNotNull(role);
			assertEquals("guest", role.getName());
			assertEquals(1l, (long) role.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeEntries();
		}
	}
	
	@Test
	public synchronized void testRemove() {
		insertEntries();
		try {
			roleDao.remove(new Role(1l, "guest"));
			Role role = roleDao.findByName("guest");
			assertNull(role);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeEntries();
		}
	}

	@Test
	public synchronized void testUpdate() {
		insertEntries();
		try {
			assertNotNull(roleDao.findByName("guest"));
			roleDao.update(new Role(1l, "m"));
			Role role = roleDao.findByName("m");
			assertNotNull(role);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeEntries();
		}
	}
	
	@Test
	public synchronized void testFind() {
		insertEntries();
		try {
			Role role = roleDao.findByName("guest");
			assertNotNull(role);
			assertEquals(1l, (long) role.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			removeEntries();
		}
	}
}
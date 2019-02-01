package com.nixsolutions.webapp.dbunit;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

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
import com.nixsolutions.webapp.dao.JdbcRoleDao;
import com.nixsolutions.webapp.dao.tables.Role;

public class RoleDaoDbunitTest extends DBTestCase {
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
			RunScript.execute(connection, new FileReader(RoleDaoDbunitTest.class.getResource("/schemaRoles.sql").getPath()));
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

	public RoleDaoDbunitTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, H2URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
		// System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		try {
		String filePath = this.getClass().getResource("/roles.xml").getPath();
		return new FlatXmlDataSetBuilder().build(new FileInputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
			connection = roleDao.createConnection();
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
			Role role = roleDao.findByName("admin");
			assertNotNull(role);
			assertEquals("admin", role.getName());
			assertEquals(1l, (long) role.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testRemove() {
		try {
			roleDao.remove(new Role(1l, "admin"));
			Role role = roleDao.findByName("admin");
			assertNull(role);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void testUpdate() {
		try {
			roleDao.update(new Role(1l, "m"));
			Role role = roleDao.findByName("m");
			assertNotNull(role);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testFind() {
		try {
			Role role = roleDao.findByName("authorized");
			assertNotNull(role);
			assertEquals(2l, (long) role.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}

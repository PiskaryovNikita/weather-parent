package com.nixsolutions.webapp.dbunit;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.junit.Test;

import com.nixsolutions.webapp.dao.AbstractJdbcDao;
import com.nixsolutions.webapp.dao.JdbcRoleDao;
import com.nixsolutions.webapp.dao.RoleDao;
import com.nixsolutions.webapp.modelClasses.Role;

public class RoleDaoDbunitTest extends DBTestCase {
	private static final String H2URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
	private RoleDao roleDao = new JdbcRoleDao();

	public void createTable() throws Exception {
		AbstractJdbcDao.setH2URL(H2URL);
		Connection connection = null;
		connection = DriverManager.getConnection(H2URL, "sa", "");
		RunScript.execute(connection, new FileReader(RoleDaoDbunitTest.class.getResource("/schemaRoles.sql").getPath()));
		connection.close();
	}

	public RoleDaoDbunitTest(String name) throws Exception {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, H2URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
		
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		String filePath = this.getClass().getResource("/roles.xml").getPath();
		return new FlatXmlDataSetBuilder().build(new FileInputStream(filePath));
	}
	
	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {
		super.setUpDatabaseConfig(config);
		try {
			createTable();
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

	@Test
	public void testCreate() throws Exception {
		Role role = roleDao.findByName("admin");
		assertNotNull(role);
		assertEquals("admin", role.getName());
		assertEquals(2l, (long) role.getRoleId());
	}

	@Test
	public void testRemove() throws Exception {
		roleDao.remove(new Role(1l, "admin"));
		Role role = roleDao.findByName("admin");
		assertNull(role);
	}

	@Test
	public void testUpdate() throws Exception {
		roleDao.update(new Role(1l, "m"));
		Role role = roleDao.findByName("m");
		assertNotNull(role);
	}

	@Test
	public void testFind() throws Exception {
		Role role = roleDao.findByName("admin");
		assertNotNull(role);
		assertEquals(2l, (long) role.getRoleId());
	}
}

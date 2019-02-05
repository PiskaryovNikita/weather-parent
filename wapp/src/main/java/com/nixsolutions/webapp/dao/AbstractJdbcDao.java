package com.nixsolutions.webapp.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.h2.Driver;

public abstract class AbstractJdbcDao {
	// jdbc:h2:[<path>]<databaseName>
	private static String url;
	private static String username;
	private static String password;
	private static BasicDataSource datasource = new BasicDataSource();
	protected static Logger logger = new Log4JLogger("AbstractJdbcDao").getLogger();
	
	static {
		String file = AbstractJdbcDao.class.getResource("/logconfig.properties").getPath();
		PropertyConfigurator.configure(file);
		readProperties();
		initDs();
	}

	private static void readProperties() {
		try {
			String file = AbstractJdbcDao.class.getResource("/mydb.properties").getPath();
			try (InputStream in = new FileInputStream(file)) {
				Properties properties = new Properties();
				properties.load(in);
				url = properties.getProperty("url");
				username = properties.getProperty("username");
				password = properties.getProperty("password");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initDs() {
		datasource.setDriver(new Driver());
		// use prop file
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		// holds connection opened
		datasource.setMinIdle(5);
		datasource.setMaxIdle(10);
		datasource.setMaxOpenPreparedStatements(20);
		datasource.setDefaultAutoCommit(false);
	}

	public Connection createConnection() throws SQLException {
		try {
			Connection connection = datasource.getConnection();
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.trace(this.hashCode() + " object ", e);
			throw e;
		}
	}

	public static void setH2URL(String h2url) {
		url = h2url;
		datasource.setUrl(url);
	}
	
	@Override
	protected void finalize() throws Throwable {
		datasource.close();
	}
}
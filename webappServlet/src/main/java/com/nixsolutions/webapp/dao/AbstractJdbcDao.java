package com.nixsolutions.webapp.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.h2.Driver;

public abstract class AbstractJdbcDao {
	// jdbc:h2:[<path>]<databaseName>
	private static String url;
	private static String username;
	private static String password;
	private static BasicDataSource datasource = new BasicDataSource();
	private static Logger logger = Logger.getLogger(AbstractJdbcDao.class);

	static {
		try {
			readProperties();
		} catch (Exception e) {
			logger.error("AbsJdbcDao", e);
			throw new ExceptionInInitializerError(e);
		}
		initDs();
	}

	private static void readProperties() throws Exception {
		try (InputStream in = AbstractJdbcDao.class
				.getResourceAsStream("/mydb.properties")) {
			Properties properties = new Properties();
			properties.load(in);
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
		} catch (Exception e) {
			logger.error("AbsJdbcDao", e);
			throw e;
		}
	}

	private static void initDs() {
		datasource.setDriver(new Driver());
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
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
			logger.error("AbsJdbcDao", e);
			throw e;
		}
	}
	public static void setH2URL(String h2url) {
		url = h2url;
		datasource.setUrl(url);
	}
}

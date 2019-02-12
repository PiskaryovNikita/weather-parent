package com.nixsolutions.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.nixsolutions.webapp.modelClasses.Role;

public class JdbcRoleDao extends AbstractJdbcDao implements RoleDao {
	private static Logger logger = Logger.getLogger(JdbcRoleDao.class);

	@Override
	public void create(Role role) throws SQLException {
		Connection connection = createConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO roles(role_id, name) VALUES(?, ?);");
			ps.setLong(1, role.getRoleId());
			ps.setString(2, role.getName());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			logger.error("JdbcRoleDao", e);
			connection.rollback();
			throw e;
		} finally {
			connection.close();
		}
	}

	@Override
	public void update(Role role) throws SQLException {
		Connection connection = createConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE roles SET name = ? WHERE role_id = ?;");
			ps.setString(1, role.getName());
			ps.setLong(2, role.getRoleId());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			logger.error("JdbcRoleDao", e);
			connection.rollback();
			throw e;
		} finally {
			connection.close();
		}
	}

	@Override
	public void remove(Role role) throws SQLException {
		Connection connection = createConnection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM roles WHERE name = ?;");
			ps.setString(1, role.getName());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			logger.error("JdbcRoleDao", e);
			connection.rollback();
			throw e;
		} finally {
			connection.close();
		}
	}

	@Override
	public Role findByName(String name) throws SQLException {
		Connection connection = null;
		try {
			connection = createConnection();
			PreparedStatement ps = connection.prepareStatement(
					"SELECT role_id, name FROM roles WHERE name = ?;");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Role(rs.getLong("role_id"), rs.getString("name"));
			}
			return null;
		} catch (SQLException e) {
			logger.error("JdbcRoleDao", e);
			throw e;
		} finally {
			connection.close();
		}
	}
}

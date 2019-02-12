package com.nixsolutions.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nixsolutions.webapp.modelClasses.Role;
import com.nixsolutions.webapp.modelClasses.User;

public class JdbcUserDao extends AbstractJdbcDao implements UserDao {
	private static Logger logger = Logger.getLogger(JdbcUserDao.class);

	@Override
	public void create(User user) throws SQLException {
		Connection connection = createConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO users(login, password, email, firstName, lastName, birthday, role_id) VALUES(?, ?, ?, ?, ?, ?, ?);");
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getFirstName());
			ps.setString(5, user.getLastName());
			ps.setDate(6, user.getBirthday());
			ps.setLong(7, user.getRole().getRoleId());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			logger.error("JdbcUserDao", e);
			connection.rollback();
			throw e;
		} finally {
			connection.close();
		}
	}

	@Override
	public void update(User user) throws SQLException {
		Connection connection = createConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE users SET password = ?, email = ?, firstName = ?, "
							+ "lastName = ?, birthday = ?, role_id = ? WHERE login = ?;");
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setDate(5, user.getBirthday());
			ps.setLong(6, user.getRole().getRoleId());
			ps.setString(7, user.getLogin());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			logger.error("JdbcUserDao", e);
			connection.rollback();
			throw e;
		} finally {
			connection.close();
		}
	}

	@Override
	public void remove(User user) throws SQLException {
		Connection connection = createConnection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("DELETE FROM users WHERE login = ?;");
			ps.setString(1, user.getLogin());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			logger.error("JdbcUserDao", e);
			connection.rollback();
			throw e;
		} finally {
			connection.close();
		}
	}

	@Override
	public List<User> findAll() throws SQLException {
		Connection connection = createConnection();
		List<User> users = new LinkedList<>();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"SELECT user_id, login, password, email, firstName, lastName, birthday, users.role_id, name "
							+ "FROM (users INNER JOIN roles ON users.role_id = roles.role_id);");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Role role = new Role(rs.getLong("users.role_id"),
						rs.getString("name"));
				users.add(new User(rs.getLong("user_id"), rs.getString("login"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("birthday"), role));
			}
			return users;
		} catch (SQLException e) {
			logger.error("JdbcUserDao", e);
			throw e;
		} finally {
			connection.close();
		}
	}

	@Override
	public User findByLogin(String login) throws SQLException {
		Connection connection = createConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"SELECT user_id, login, password, email, firstName, lastName, birthday, users.role_id, name FROM "
							+ "(users INNER JOIN roles ON users.role_id = roles.role_id) WHERE login = ?;");
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Role role = new Role(rs.getLong("users.role_id"),
						rs.getString("name"));
				return new User(rs.getLong("user_id"), rs.getString("login"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("birthday"), role);
			}
			return null;
		} catch (SQLException e) {
			logger.error("JdbcUserDao", e);
			throw e;
		} finally {
			connection.close();
		}
	}

	@Override
	public User findByEmail(String email) throws SQLException {
		Connection connection = createConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(
					"SELECT user_id, login, password, email, firstName, lastName, birthday, users.role_id, name "
							+ "FROM (users INNER JOIN roles ON users.role_id = roles.role_id) WHERE email = ?;");
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Role role = new Role(rs.getLong("users.role_id"),
						rs.getString("name"));
				return new User(rs.getLong("user_id"), rs.getString("login"),
						rs.getString("password"), rs.getString("email"),
						rs.getString("firstName"), rs.getString("lastName"),
						rs.getDate("birthday"), role);
			}
			return null;
		} catch (SQLException e) {
			logger.error("JdbcUserDao", e);
			throw e;
		} finally {
			connection.close();
		}
	}
}

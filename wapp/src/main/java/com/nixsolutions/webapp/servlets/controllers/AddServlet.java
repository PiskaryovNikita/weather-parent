package com.nixsolutions.webapp.servlets.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.dao.tables.User;

public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final JdbcUserDao userDao = new JdbcUserDao();

	// add
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> map = ParseUtils.parseRequestBody(request);
		User createdUser = ParseUtils.getBean(map);
		if (createdUser != null) {
			try {
				userDao.create(createdUser);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		User user = (User) request.getSession(false).getAttribute("user");
		if (user.getRole().getName().equals("user")) {
			getServletContext().getRequestDispatcher("/UserHomePage.jsp").forward(request, response);
		} else {
			try {
				request.getSession(false).setAttribute("users", userDao.findAll());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/AdminHomePage.jsp").forward(request, response);
		}
	}
}

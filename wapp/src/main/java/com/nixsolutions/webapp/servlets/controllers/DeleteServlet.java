package com.nixsolutions.webapp.servlets.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.dao.tables.User;

public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final JdbcUserDao userDao = new JdbcUserDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		if (login != null && !login.equals("")) {
			User user2Del = new User(login);
			try {
				userDao.remove(user2Del);
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

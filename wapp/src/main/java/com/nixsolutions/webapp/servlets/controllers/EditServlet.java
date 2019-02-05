package com.nixsolutions.webapp.servlets.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.dao.UserDao;
import com.nixsolutions.webapp.dao.tables.User;

public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserDao userDao = new JdbcUserDao();
	
	// edit, deploy res with possible replacing
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			Map<String, String> map = ParseUtils.parseRequestBody(request);
			User updatedUser = ParseUtils.getBean(map);
			if (updatedUser != null) {
				try {
					userDao.update(updatedUser);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			User user = (User) request.getSession().getAttribute("user");
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

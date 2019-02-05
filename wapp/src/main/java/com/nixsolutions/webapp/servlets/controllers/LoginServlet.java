package com.nixsolutions.webapp.servlets.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.dao.tables.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final JdbcUserDao userDao = new JdbcUserDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = (String) request.getParameter("login");
		User user = null;
		if (request.getSession(false) != null || login != null) {
			try {
				if (login != null) {
					user = userDao.findByLogin(login);
				} else {
					user = (User) request.getSession(false).getAttribute("user");
				}
				if (user != null) {
					request.getSession().setAttribute("user", user);//create session obj
					if (user.getRole().getName().equals("user")) {
						getServletContext().getRequestDispatcher("/UserHomePage.jsp").forward(request, response);
					} else {
						List<User> users = userDao.findAll();
						request.getSession().setAttribute("users", users);
						getServletContext().getRequestDispatcher("/AdminHomePage.jsp").forward(request, response);
					}
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
		}
		response.sendRedirect("/login");
	}
}

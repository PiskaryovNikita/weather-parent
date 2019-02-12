package com.nixsolutions.webapp.servlets.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.modelClasses.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final JdbcUserDao userDao = new JdbcUserDao();
	private static final Logger logger = new Log4JLogger("LoginServlet").getLogger();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = (String) request.getParameter("login");
		String password = (String) request.getParameter("password");
		User user = null;
		if (request.getSession(false) != null || login != null) {
			try {
				if (login != null) {
					user = userDao.findByLogin(login);
					if (!user.getPassword().equals(password)) {
						user = null;
					}
				} else {
					user = (User) request.getSession(false).getAttribute("user");
				}
				if (user != null) {
					// create session obj
					request.getSession().setAttribute("user", user);
					if (user.getRole().getName().equals("user")) {
						getServletContext()
								.getRequestDispatcher("/UserHomePage.jsp")
								.forward(request, response);
					} else {
						List<User> users = userDao.findAll();
						request.getSession().setAttribute("users", users);
						getServletContext()
								.getRequestDispatcher("/AdminHomePage.jsp")
								.forward(request, response);
					}
					return;
				}
			} catch (Exception e) {
				logger.error("LoginServlet", e);
				throw new ServletException(e);
			}
		}
		// if no entry was found in db
		request.getSession().setAttribute("warning", "You login/password is incorrect!\nPlease try again.");
		response.sendRedirect("/login");
	}
}

package com.nixsolutions.webapp.servlets.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

import com.nixsolutions.webapp.dao.JdbcUserDao;
import com.nixsolutions.webapp.modelClasses.User;
import com.nixsolutions.webapp.utils.MyUtils;

public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final JdbcUserDao userDao = new JdbcUserDao();
	private static final Logger logger = new Log4JLogger("AddServlet")
			.getLogger();

	// add
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> map = MyUtils.parseRequestBody(request);
		User createdUser = MyUtils.getBean(map);
		if (createdUser != null) {
			try {
				userDao.create(createdUser);
			} catch (SQLException e) {
				logger.error("AddServlet", e);
				throw new ServletException(e);
			}
		}
		User user = (User) request.getSession(false).getAttribute("user");
		if (user.getRole().getName().equals("user")) {
			getServletContext().getRequestDispatcher("/UserHomePage.jsp")
					.forward(request, response);
		} else {
			try {
				request.getSession(false).setAttribute("users",
						userDao.findAll());
			} catch (SQLException e) {
				logger.error("AddServlet", e);
				throw new ServletException(e);
			}
			getServletContext().getRequestDispatcher("/AdminHomePage.jsp")
					.forward(request, response);
		}
	}
}

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
import com.nixsolutions.webapp.dao.UserDao;
import com.nixsolutions.webapp.modelClasses.User;
import com.nixsolutions.webapp.utils.MyUtils;

public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserDao userDao = new JdbcUserDao();
	private static final Logger logger = new Log4JLogger("EditServlet")
			.getLogger();

	// edit, deploy res with possible replacing
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> map = MyUtils.parseRequestBody(request);
		User updatedUser = MyUtils.getBean(map);
		if (updatedUser != null) {
			try {
				userDao.update(updatedUser);
			} catch (SQLException e) {
				logger.error("EditServlet", e);
				throw new ServletException(e);
			}
		}
		User user = (User) request.getSession().getAttribute("user");
		if (user.getRole().getName().equals("user")) {
			getServletContext().getRequestDispatcher("/UserHomePage.jsp")
					.forward(request, response);
		} else {
			try {
				request.getSession(false).setAttribute("users",
						userDao.findAll());
			} catch (SQLException e) {
				logger.error("EditServlet", e);
				throw new ServletException(e);
			}
			getServletContext().getRequestDispatcher("/AdminHomePage.jsp")
					.forward(request, response);
		}
	}
}

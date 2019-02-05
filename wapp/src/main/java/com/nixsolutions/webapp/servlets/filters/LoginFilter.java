package com.nixsolutions.webapp.servlets.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpFilter;

public class LoginFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String url = request.getRequestURI();
		HttpSession session = request.getSession(false);
		if (session == null && !url.equals("/login") && !url.equals("/home")) {
			response.sendRedirect("/login");
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}

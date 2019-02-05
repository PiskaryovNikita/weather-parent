package com.nixsolutions.webapp.servlets.controllers;

import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.nixsolutions.webapp.dao.tables.Role;
import com.nixsolutions.webapp.dao.tables.User;

public class ParseUtils {
	public static Map<String, String> parseRequestBody(HttpServletRequest request) {
		try {
			Map<String, String> map = new TreeMap<>();
			String s = "", key = "";
			int chval;
			try {
				Reader reader = request.getReader();
				while ((chval = reader.read()) != -1) {
					char ch = (char) chval;
					if (ch == '=') {
						key = s;
						s = "";
						continue;
					} else if (ch == '&') {
						map.put(key, s);
						s = "";
						continue;
					}
					s += ch;
				}
				map.put(key, s);
				return map;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public static User getBean(Map<String, String> map) {
		try {
			Date birthdayDate = null;
			String login = map.get("login");
			if (login != null && !login.equals("")) {
				String birthdayStr = map.get("birthday");
				if (birthdayStr.length() == 10) {
					int year = Integer.parseInt(birthdayStr.substring(0, 4));
					int month = Integer.parseInt(birthdayStr.substring(5, 7));
					int day = Integer.parseInt(birthdayStr.substring(8, 10));
					birthdayDate = new Date(year, month, day);
				}
				Role role = null;
				String name = map.get("role");
				if (name != null && name.equals("Admin")) {
					role = new Role(2l, "Admin");
				} else if (name != null && name.equals("User")) {
					role = new Role(1l, "User");
				}
				return new User(login, map.get("password"), map.get("email"), map.get("firstName"), map.get("lastName"),
						birthdayDate, role);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

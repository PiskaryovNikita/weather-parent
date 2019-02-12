package com.nixsolutions.webapp.utils;

import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.nixsolutions.webapp.modelClasses.Role;
import com.nixsolutions.webapp.modelClasses.User;

public class MyUtils {

	public static Map<String, String> parseRequestBody(
			HttpServletRequest request) {
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

	public static User getBean(Map<String, String> map) {
		Date birthdayDate = null;
		Role role = null;
		String login = map.get("login");
		String password = map.get("password");
		if (login != null && !login.equals("") && password != null
				&& !password.equals("")) {
			String birthdayStr = map.get("birthday");
			if (birthdayStr != null && birthdayStr.length() == 10) {
				try {
					birthdayDate = Date.valueOf(birthdayStr);
				} catch (Exception e) {
					e.printStackTrace();
					birthdayDate = null;
				}
			}
			String name = map.get("role");
			if (name != null && name.equals("Admin")) {
				role = new Role(2l, "Admin");
			} else if (name != null && name.equals("User")) {
				role = new Role(1l, "User");
			}
			return new User(login, password, map.get("email"),
					map.get("firstName"), map.get("lastName"), birthdayDate,
					role);
		}
		// no login&password -  no entry
		return null;
	}
}

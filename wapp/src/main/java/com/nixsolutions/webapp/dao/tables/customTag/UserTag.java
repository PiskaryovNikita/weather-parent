package com.nixsolutions.webapp.dao.tables.customTag;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.nixsolutions.webapp.dao.tables.Role;
import com.nixsolutions.webapp.dao.tables.User;

public class UserTag implements Tag {
	private PageContext pageContext;
	private List<User> users;

	public UserTag() {
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	// outputs table
	@SuppressWarnings("deprecation")
	private String getUsersTable() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String output = "<table>" + "<tr><td>Login</td><td>First Name</td><td>Last Name</td>"
				+ "<td>Age</td><td>Role</td><td>Actions</td></tr>";
		for (User user : users) {
			String lfnln = "<tr><td>" + user.getLogin() + "</td>" + "<td>" + user.getFirstName() + "</td>" + "<td>"
					+ user.getLastName() + "</td>";
			String age; 
			if (user.getBirthday() != null) {
			age = "<td>" + (year - user.getBirthday().getYear()) + "</td>";
			} else {
				age = "<td>unknown</td>";
			}
			String role = "<td>" + user.getRole().getName() + "</td>";
			String actions = "<td>" + "<a href=\"http://localhost:8080/editpage?login=" + user.getLogin()
					+ "\">Edit</a>  " + "  <a href=\"http://localhost:8080/adminDelete?login=" + user.getLogin() + "\" "
					+ "onclick=\"return confirm('Are you sure you want to delete this item?');\">Delete</a>"
					+ "</td></tr>";
			output += lfnln + age + role + actions;
		}
		return output + "</table>";
	}

	@Override
	public void setPageContext(PageContext pc) {
		this.pageContext = pc;
	}

	@Override
	public void setParent(Tag t) {
	}

	@Override
	public Tag getParent() {
		return null;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(getUsersTable());
		} catch (IOException e) {
			try {
				pageContext.getOut().write("Error while parsing users!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return Tag.SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		// go on parsing the jsp page
		return Tag.EVAL_PAGE;
	}

	@Override
	public void release() {
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		User user1 = new User("qwe", "qwerty", "asd", "a", "b", new Date(1998, 01, 15), new Role(1l, "user"));
		User user2 = new User("123qwe", "5657qwerty", "789789asd", "8a", "0b", new Date(1969, 10, 25),
				new Role(1l, "admin"));
		List<User> users = Arrays.asList(user1, user2);
		UserTag userTag = new UserTag();
		userTag.setUsers(users);
		String res = userTag.getUsersTable();
		System.out.println(res);
	}
}

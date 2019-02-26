package com.nixsolutions.wsxx.resource;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.nixsolutions.webapp.dao.UserDao;
import com.nixsolutions.webapp.modelClasses.User;

@Path("/users")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class UserResource {
	@Autowired
	private UserDao userDao; 

	@GET
	public List<User> getIt() throws SQLException {
		return userDao.findAll();
	}
}

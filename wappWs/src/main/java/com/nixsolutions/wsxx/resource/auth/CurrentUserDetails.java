package com.nixsolutions.wsxx.resource.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.nixsolutions.webapp.model.Role;
import com.nixsolutions.webapp.model.User;

@Path("/userDetails")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CurrentUserDetails {

	@GET
	public User user() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User(userDetails.getUsername(), new Role(userDetails.getAuthorities().toArray()[0] + ""));
		return user;
	}
}

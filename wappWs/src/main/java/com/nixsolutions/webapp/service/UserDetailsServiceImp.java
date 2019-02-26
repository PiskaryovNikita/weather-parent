package com.nixsolutions.webapp.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nixsolutions.webapp.dao.UserDao;
import com.nixsolutions.webapp.modelClasses.User;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	private static final Logger logger = Logger.getLogger(UserDetailsServiceImp.class);

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user;
		try {
			user = userDao.findByLogin(userName);
		} catch (Exception e) {
			logger.error("loadUserByUsername", e);
			throw new RuntimeException("excbyload", e);
		}
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(userName);
		if (user != null) {
			builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
			String authorities;
			if (user.getRole().getRoleId().equals(2L)) {
				authorities = "ADMIN";
			} else {
				authorities = "USER";
			}
			builder.authorities(authorities);
		} else {
			builder.authorities("ANONYMOUS");
		}
		return builder.build();
	}
}

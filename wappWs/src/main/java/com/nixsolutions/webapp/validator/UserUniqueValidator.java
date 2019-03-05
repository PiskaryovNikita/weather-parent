package com.nixsolutions.webapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nixsolutions.webapp.model.User;
import com.nixsolutions.webapp.persistence.UserDao;

@Service(value = "userValidator")
public class UserUniqueValidator implements Validator {
	@Autowired
	private UserDao userDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if (userDao.findByLogin(user.getLogin()) != null) {
			errors.rejectValue("login", "", "non-unique login");
		}
		if (userDao.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "", "non-unique email");
		}
		if (user.getBirthday() == null) {
			errors.rejectValue("role", "", "enter a correct date, YYYY-MM-DD");
		}
	}
}

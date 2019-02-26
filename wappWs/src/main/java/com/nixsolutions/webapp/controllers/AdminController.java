package com.nixsolutions.webapp.controllers;

import java.security.Principal;
import java.sql.SQLException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nixsolutions.webapp.dao.RoleDao;
import com.nixsolutions.webapp.dao.UserDao;
import com.nixsolutions.webapp.modelClasses.Role;
import com.nixsolutions.webapp.modelClasses.User;

@Controller
public class AdminController {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	@Qualifier("userValidator")
	private Validator userValidator;

	@GetMapping({ "/", "/home" })
	public String showHome(Principal principal, Model model) throws SQLException {
		String login = principal.getName();
		User user = userDao.findByLogin(login);
		if (user.getRole().getRoleId() == 1l) {
			return "UserHomePage" ;
		} else {
			model.addAttribute("users", userDao.findAll());
			return "AdminHomePage";
		} 
	}

	@GetMapping("/adminDelete")
	public String adminDelete(Principal principal, Model model, @RequestParam("userId") Long userId)
			throws SQLException {
		userDao.remove(new User(userId));
		return "redirect:/home";
	}

	@GetMapping("/adminEdit")
	public String editPage(Model model, @RequestParam("userId") Long userId) throws SQLException {
		model.addAttribute("user", userDao.findById(userId));
		model.addAttribute("roles", roleDao.findAll());
		return "AdminEditPage";
	}

	@PostMapping("/adminEdit")
	public String adminEdit(@Valid User user, BindingResult bindingResult, Model model,
			@RequestParam("role") String role) throws SQLException {
		user.setRole(roleDao.findByName(role));
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("roles", roleDao.findAll());
			return "AdminEditPage";
		}
		userDao.update(user);
		return "redirect:/home";
	}

	@GetMapping("/adminAdd")
	public String addPage(Model model) throws SQLException {
		model.addAttribute("user", new User(new Role(1l, "User")));
		model.addAttribute("roles", roleDao.findAll());
		return "AdminAddPage";
	}

	@PostMapping("/adminAdd")
	public String adminAdd(@Valid User user, BindingResult bindingResult, Model model, @RequestParam("role") String role)
			throws SQLException {
		userValidator.validate(user, bindingResult);
		user.setRole(roleDao.findByName(role));
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("roles", roleDao.findAll());
			return "AdminAddPage";
		}
		userDao.create(user);
		model.addAttribute("users", userDao.findAll());
		return "redirect:/home";
	}
}

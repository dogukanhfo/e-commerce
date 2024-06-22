package com.project.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.ecommerce.services.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin/users")
	public String getUsers(Model model) {
		model.addAttribute("users", userService.getUsersByRole("USER"));
		return "admin-users";
	}
	
	@GetMapping("/admin/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		
		return "redirect:/admin/users";
	}
}

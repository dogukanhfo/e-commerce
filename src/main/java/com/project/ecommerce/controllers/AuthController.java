package com.project.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.ecommerce.models.UserEntity;
import com.project.ecommerce.services.UserService;

@Controller
public class AuthController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String getRegisterForm(Model model) {
		UserEntity user = new UserEntity();
		model.addAttribute("user", user);
		return "register";
	}
	
	@PostMapping("/register/save")
	public String registerUser(@ModelAttribute("user") UserEntity user) {
		userService.saveUser(user);
		return "redirect:/login";
	}
}

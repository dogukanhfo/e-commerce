package com.project.ecommerce.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	@GetMapping("/home")
	public String homePage(Authentication authentication) {
		if (authentication.getAuthorities().iterator().next().toString().equals("USER")) {
			return "redirect:/products";
		} else {
			return "redirect:/admin/home";
		}
	}
}

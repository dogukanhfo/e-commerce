package com.project.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.ecommerce.models.Cart;
import com.project.ecommerce.models.Product;
import com.project.ecommerce.services.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public String viewCart(HttpSession session, Model model) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}
		model.addAttribute("cart", cart);
		return "cart";
	}
	
	@PostMapping("/add")
	public String addToCart(@RequestParam Long productId, @RequestParam Integer quantity, HttpSession session) {
		Product product = productService.getProductById(productId);
		if (product != null) {
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
				cart = new Cart();
				session.setAttribute("cart", cart);
			}
			cart.addItem(product, quantity);
		}
		return "redirect:/cart";
	}
	
	@PostMapping("/remove")
	public String removeFromCart(@RequestParam Long productId, HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Product product = productService.getProductById(productId);
			cart.removeItem(product);
		}
		return "redirect:/cart";
	}
}

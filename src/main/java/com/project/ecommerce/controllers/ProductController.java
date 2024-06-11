package com.project.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.ecommerce.models.Product;
import com.project.ecommerce.services.ProductService;

@Controller
@RequestMapping(value = "/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping()
	public String getAllProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}
	
	@GetMapping("/{name}-{id}")
	public String getProduct(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "product";
	}
	
	@GetMapping("/new")
	public String createProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "create-product";
	}
	
	@PostMapping("/new")
	public String saveProduct(@ModelAttribute("product") Product product) {
		productService.saveProduct(product);
		return "redirect:/products";
	}
	
	@GetMapping("/update/{id}")
	public String updateProductForm(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "update-product";
	}
	
	@PostMapping("/update/{id}")
	public String updateProduct(@ModelAttribute("product") Product product, @PathVariable Long id) {
		productService.updateProduct(product, id);
		return "redirect:/products";
	}
}

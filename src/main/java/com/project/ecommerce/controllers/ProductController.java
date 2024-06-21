package com.project.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.ecommerce.models.Product;
import com.project.ecommerce.services.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public String getAllProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@GetMapping("/products/search")
	public String searchProduct(@RequestParam(value="query") String query, Model model) {
		List<Product> products = productService.searchProducts(query);
		model.addAttribute("products", products);
		return "products";
	}
	
	@GetMapping("/admin/products")
	public String getAllAdminProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "admin-products";
	}
	
	@GetMapping("/products/{name}-{id}")
	public String getProduct(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "product";
	}
	
	@GetMapping("/admin/products/new")
	public String createProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "create-product";
	}
	
	@PostMapping("/admin/products/new")
	public String saveProduct(@ModelAttribute("product") Product product) {
		productService.saveProduct(product);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/products/update/{id}")
	public String updateProductForm(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "update-product";
	}
	
	@PostMapping("/admin/products/update/{id}")
	public String updateProduct(@ModelAttribute("product") Product product, @PathVariable Long id) {
		productService.updateProduct(product, id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/products/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/products/search")
	public String searchAdminProduct(@RequestParam(value="query") String query, Model model) {
		List<Product> products = productService.searchProducts(query);
		model.addAttribute("products", products);
		return "admin-products";
	}
}

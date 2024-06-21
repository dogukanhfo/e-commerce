package com.project.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.ecommerce.models.Order;
import com.project.ecommerce.models.OrderItem;
import com.project.ecommerce.models.Product;
import com.project.ecommerce.services.OrderService;
import com.project.ecommerce.services.ProductService;
import com.project.ecommerce.services.UserService;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired 
	private UserService userService;
	
	@PostMapping("/orders/new")
	public String createOrder(@RequestParam Long customerId, @RequestParam List<Long> productIds, @RequestParam List<Integer> quantities, @RequestParam String paymentMethod) {
		List<OrderItem> orderItems = new ArrayList<>();
		
		for (int i = 0; i < productIds.size(); i++) {
			Product product = productService.getProductById(productIds.get(i));
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setQuantity(quantities.get(i));
			orderItem.setPrice(product.getPrice() * quantities.get(i));
			orderItems.add(orderItem);
		}
		
		orderService.createOrder(customerId, orderItems, paymentMethod);
		return "redirect:/orders";
	}
	
	@GetMapping("/admin/orders")
	public String getAllOrders(Model model) {
		model.addAttribute("orders", orderService.getAllOrders());
		return "orders";
	}
	
	@GetMapping("/orders/{id}")
	public String getOrderById(@PathVariable Long id, Model model) {
		model.addAttribute("order", orderService.getOrderById(id));
		return "order";
	}
	
	@GetMapping("/orders/delete/{id}")
	public String deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return "redirect:/orders";
	}
	
	@GetMapping("/orders")
	public String getCustomerOrders(Authentication authentication, Model model) {
		String username = authentication.getName();
		Long customerId = userService.getUserByUsername(username).getId();
		if (customerId != null) {
			List<Order> orders = orderService.getOrdersByCustomerId(customerId);
			model.addAttribute("orders", orders);
		} else {
			model.addAttribute("orders", new ArrayList<>());
		}
		return "orders";
	}
}

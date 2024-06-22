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
	private UserService userService;
	
	@GetMapping("/admin/orders")
	public String getAllOrders(Model model) {
		List<Order> pendingOrders = orderService.getOrdersByStatus("Pending");
		List<Order> processingOrders = orderService.getOrdersByStatus("Processing");
		List<Order> shippedOrders = orderService.getOrdersByStatus("Shipped");
		
		model.addAttribute("pendingOrders", pendingOrders);
		model.addAttribute("processingOrders", processingOrders);
		model.addAttribute("shippedOrders", shippedOrders);
		return "admin-orders";
	}
	
	@GetMapping("/admin/orders/{id}")
	public String getOrderAdmin(@PathVariable Long id, Model model) {
		model.addAttribute("order", orderService.getOrderById(id));
		
		return "admin-order";
	}
	
	@GetMapping("/admin/orders/{id}/process")
	public String updateStatusToProcess(@PathVariable Long id) {
		orderService.updateOrderStatus(id, "Processing");
		
		return "redirect:/admin/orders";
	}
	
	@GetMapping("/admin/orders/{id}/ship")
	public String updateStatusToShip(@PathVariable Long id) {
		orderService.updateOrderStatus(id, "Shipped");
		
		return "redirect:/admin/orders";
	}
	
	@GetMapping("/admin/orders/delete/{id}")
	public String deleteOrderAdmin(@PathVariable Long id) {
		orderService.deleteOrder(id);
		
		return "redirect:/admin/orders";
	}
	
	@GetMapping("/orders/{id}")
	public String getOrderById(@PathVariable Long id, Model model) {
		model.addAttribute("order", orderService.getOrderById(id));
		return "order";
	}
	
	@GetMapping("/orders/delete/{id}")
	public String deleteOrder(@PathVariable Long id, Model model) {
		Order order = orderService.getOrderById(id);
		if (order.getStatus().equals("Pending")) {
			orderService.deleteOrder(id);
			return "redirect:/orders";			
		}
		model.addAttribute("order", order);
		model.addAttribute("error", true);
		return "/order";
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

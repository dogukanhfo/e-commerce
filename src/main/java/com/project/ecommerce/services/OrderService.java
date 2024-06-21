package com.project.ecommerce.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.models.Order;
import com.project.ecommerce.models.OrderItem;
import com.project.ecommerce.models.UserEntity;
import com.project.ecommerce.repositories.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserService userService;
	
	public Order createOrder(Long customerId, List<OrderItem> orderItems, String paymentMethod) {
		UserEntity customer = userService.getUserById(customerId);
		Order order = new Order();
		order.setCustomer(customer);
		order.setOrderItems(orderItems);
		order.setOrderDate(LocalDateTime.now());
		order.setStatus("Pending");
		order.setPaymentMethod(paymentMethod);
		for (OrderItem item : orderItems) {
			item.setOrder(order);
		}
		return orderRepository.save(order);
	}
	
	public Order updateOrderStatus(Long customerId, String status) {
		Order order = getOrderById(customerId);
		order.setStatus(status);
		
		return orderRepository.save(order);
	}
	
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}
	
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}
	
	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);
	}
	
	public List<Order> getOrdersByCustomerId(Long customerId) {
		return orderRepository.findByCustomerId(customerId);
	}
	
	public List<Order> getOrdersByStatus(String status) {
		return orderRepository.findByStatus(status);
	}
}

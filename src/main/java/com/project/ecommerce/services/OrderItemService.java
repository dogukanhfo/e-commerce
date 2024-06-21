package com.project.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.models.Order;
import com.project.ecommerce.models.OrderItem;
import com.project.ecommerce.models.Product;
import com.project.ecommerce.repositories.OrderItemRepository;

@Service
public class OrderItemService {
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public List<OrderItem> getOrderItemsByOrder(Order order) {
		return orderItemRepository.findByOrder(order);
	}
	
	public OrderItem createOrderItem(Order order, Product product, Integer quantity, Float price) {
		OrderItem orderItem = new OrderItem();
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setQuantity(quantity);
		orderItem.setPrice(price);
		return orderItemRepository.save(orderItem);
	}
}

package com.project.ecommerce.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<CartItem> items = new ArrayList<>();
	
	public void addItem(Product product, Integer quantity) {
		for (CartItem item: items) {
			if (item.getProduct().getId().equals(product.getId())) {
				item.setQuantity(item.getQuantity() + quantity);
				return;
			}
		}
		items.add(new CartItem(product, quantity));
	}
	
	public void removeItem(Product product) {
		items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
	}
	
	public List<CartItem> getItems() {
		return items;
	}
	
	public Float getTotalAmount() {
		return (float) items.stream().mapToDouble(CartItem::getTotalPrice).sum();
	}
}

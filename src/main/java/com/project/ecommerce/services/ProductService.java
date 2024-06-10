package com.project.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.models.Product;
import com.project.ecommerce.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	//Read
	public Iterable<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElse(null);
	}
	
	//Create
	public void saveProduct(Product product) {
		productRepository.save(product);
	}
	
	//Delete
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	//Update
	public void updateProduct(Product product, Long productId) {
		Product p = productRepository.findById(productId).orElse(null);
		
		if (p != null) {
			p.SetName(product.getName());
			p.SetDescription(product.getDescription());
			p.setQuantity(product.getQuantity());
			p.setPrice(product.getPrice());
			productRepository.save(p);
		} else {
			System.out.print("Given productId does not matches any product.");
		}
	}
}

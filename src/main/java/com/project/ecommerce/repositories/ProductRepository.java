package com.project.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.ecommerce.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
	@Query("select p from Product p where p.name like concat('%', :query, '%')")
	List<Product> searchProducts(String query);
}

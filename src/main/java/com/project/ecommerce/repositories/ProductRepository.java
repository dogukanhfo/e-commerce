package com.project.ecommerce.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.ecommerce.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}

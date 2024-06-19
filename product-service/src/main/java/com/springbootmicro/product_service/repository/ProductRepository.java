package com.springbootmicro.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springbootmicro.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
    
}

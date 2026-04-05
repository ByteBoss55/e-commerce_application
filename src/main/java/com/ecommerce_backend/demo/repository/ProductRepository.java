package com.ecommerce_backend.demo.repository;

import org.springframework.stereotype.Repository;

import com.ecommerce_backend.demo.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

package com.ecommerce_backend.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_backend.demo.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

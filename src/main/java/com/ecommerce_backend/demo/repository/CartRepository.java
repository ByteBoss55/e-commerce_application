package com.ecommerce_backend.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_backend.demo.model.Cart;
 
import com.ecommerce_backend.demo.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
     // Find cart by user
    Optional<Cart> findByUser(User user);
}

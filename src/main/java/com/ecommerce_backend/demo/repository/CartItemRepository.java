package com.ecommerce_backend.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_backend.demo.model.Cart;
import com.ecommerce_backend.demo.model.CartItem;
import com.ecommerce_backend.demo.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Get all items in a cart
    List<CartItem> findByCart(Cart cart);

    // Find specific product in cart
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    // Delete all items when needed
    void deleteByCart(Cart cart);
}
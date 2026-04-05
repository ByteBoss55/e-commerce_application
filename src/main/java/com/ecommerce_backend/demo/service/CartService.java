package com.ecommerce_backend.demo.service;

import java.util.List;

import com.ecommerce_backend.demo.model.CartItem;

public interface CartService {
    String addToCart(Long userId, Long productId, int quantity);
    List<CartItem> viewCart(Long userId);
    String removeItem(Long userId, Long productId);
    String updateQuantity(Long userId, Long productId, int quantity);
}

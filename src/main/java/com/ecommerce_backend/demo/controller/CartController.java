package com.ecommerce_backend.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce_backend.demo.dto.CartRequest;
import com.ecommerce_backend.demo.model.CartItem;
import com.ecommerce_backend.demo.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add to Cart API
@PostMapping("/add")
public String addToCart(@RequestBody CartRequest request) {

    return cartService.addToCart(
            request.getUserId(),
            request.getProductId(),
            request.getQuantity()
    );
}
@GetMapping
public List<CartItem> viewCart(@RequestParam Long userId) {
    return cartService.viewCart(userId);
}
@DeleteMapping("/remove")
public String removeItem(
        @RequestParam Long userId,
        @RequestParam Long productId) {

    return cartService.removeItem(userId, productId);
}
@PutMapping("/update")
public String updateQuantity(
        @RequestParam Long userId,
        @RequestParam Long productId,
        @RequestParam int quantity) {

    return cartService.updateQuantity(userId, productId, quantity);
}
}

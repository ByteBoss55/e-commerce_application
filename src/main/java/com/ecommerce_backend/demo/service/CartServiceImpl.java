package com.ecommerce_backend.demo.service;

 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce_backend.demo.model.Cart;
import com.ecommerce_backend.demo.model.CartItem;
import com.ecommerce_backend.demo.model.Product;
import com.ecommerce_backend.demo.model.User;
import com.ecommerce_backend.demo.repository.CartItemRepository;
import com.ecommerce_backend.demo.repository.CartRepository;
import com.ecommerce_backend.demo.repository.ProductRepository;
import com.ecommerce_backend.demo.repository.UserRepository;
 

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String addToCart(Long userId, Long productId, int quantity) {

        // 1. Get User
        com.ecommerce_backend.demo.model.User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get or Create Cart
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // 3. Get Product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 4. Check Stock
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        // 5. Check if product already in cart
        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem != null) {
            // Update quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Create new item
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        // 6. Save
        cartItemRepository.save(cartItem);

        return "Product added to cart successfully";
    }

    @Override
    public List<CartItem> viewCart(Long userId) {
   User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Cart cart = cartRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    return cartItemRepository.findByCart(cart);
}
@Override
public String removeItem(Long userId, Long productId) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Cart cart = cartRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    CartItem cartItem = cartItemRepository
            .findByCartAndProduct(cart, product)
            .orElseThrow(() -> new RuntimeException("Item not found in cart"));

    cartItemRepository.delete(cartItem);

    return "Item removed from cart";
}
@Override
public String updateQuantity(Long userId, Long productId, int quantity) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Cart cart = cartRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    CartItem cartItem = cartItemRepository
            .findByCartAndProduct(cart, product)
            .orElseThrow(() -> new RuntimeException("Item not found in cart"));

    if (product.getQuantity() < quantity) {
        throw new RuntimeException("Insufficient stock");
    }

    cartItem.setQuantity(quantity);
    cartItemRepository.save(cartItem);

    return "Quantity updated successfully";
}
}
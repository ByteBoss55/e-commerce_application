package com.ecommerce_backend.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce_backend.demo.model.Cart;
import com.ecommerce_backend.demo.model.CartItem;
import com.ecommerce_backend.demo.model.Order;
import com.ecommerce_backend.demo.model.OrderItem;
import com.ecommerce_backend.demo.model.Product;
import com.ecommerce_backend.demo.model.User;
import com.ecommerce_backend.demo.repository.CartItemRepository;
import com.ecommerce_backend.demo.repository.CartRepository;
import com.ecommerce_backend.demo.repository.OrderItemRepository;
import com.ecommerce_backend.demo.repository.OrderRepository;
import com.ecommerce_backend.demo.repository.ProductRepository;
import com.ecommerce_backend.demo.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String checkout(Long userId) {

        // 1. Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 3. Get cart items
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 4. Create Order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());

        double total = 0;

        // 5. Process each item
        for (CartItem item : cartItems) {

            Product product = item.getProduct();

            // Check stock
            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getName());
            }

            // Reduce stock
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());

            total += product.getPrice() * item.getQuantity();

            orderItemRepository.save(orderItem);
        }

        // 6. Set total & save order
        order.setTotalAmount(total);
        orderRepository.save(order);

        // 7. Clear cart
        cartItemRepository.deleteByCart(cart);

        return "Order placed successfully. Total amount: " + total;
    }
}
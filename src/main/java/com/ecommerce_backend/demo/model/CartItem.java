package com.ecommerce_backend.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Data;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue
    private Long id;

    // Many cart items belong to one cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // Many cart items can refer to one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}
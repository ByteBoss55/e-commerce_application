package com.ecommerce_backend.demo.controller;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce_backend.demo.model.Product;
import com.ecommerce_backend.demo.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/addproduct")
    public Product addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @GetMapping("/allproducts")
    public List<Product> getProducts() {
        return service.getAllProducts();
    }
}
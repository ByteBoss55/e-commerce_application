package com.ecommerce_backend.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce_backend.demo.dto.AuthRequest;
import com.ecommerce_backend.demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
     @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {

        // Dummy validation (replace with DB check later)
        if (request.getEmail().equals("admin@gmail.com") &&
            request.getPassword().equals("1234")) {

            return jwtUtil.generateToken(request.getEmail());
        }

        throw new RuntimeException("Invalid credentials");
    }
}

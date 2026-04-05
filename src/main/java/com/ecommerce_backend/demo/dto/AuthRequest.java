package com.ecommerce_backend.demo.dto;

import lombok.Data;

@Data
public class AuthRequest {
     private String email;
     private String password;
}

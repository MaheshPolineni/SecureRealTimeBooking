package com.example.leisuires.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.leisuires.service.AdminLoginService;

import jakarta.servlet.http.HttpSession;

@RestController
public class AdminLoginController {

    private final AdminLoginService loginService;

    public AdminLoginController(AdminLoginService loginService) {
        this.loginService = loginService;
    }

 @PostMapping("/admin/login")
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        try {
            loginService.login(email, password, session);
            return ResponseEntity.ok("Admin logged in successfully");
        } catch (IllegalArgumentException e) {
            // For invalid credentials
            return ResponseEntity.status(401).body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }
}

package com.example.leisuires.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class AdminLogoutController {

    @PostMapping("/admin/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // ❌ Destroy the session
        }
        return ResponseEntity.ok("Logged out successfully");
    }    
}

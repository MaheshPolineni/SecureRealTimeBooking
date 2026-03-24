package com.example.leisuires.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.leisuires.service.AdminSetupService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/setup")
public class AdminSetupController {

    private final AdminSetupService service;

    public AdminSetupController(AdminSetupService service) {
        this.service = service;
    }

    @PostMapping("/admin")
    public ResponseEntity<String> setupAdmin(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request) {

        try {
            String clientIp = request.getRemoteAddr();
            String remoteHost = request.getRemoteHost();
            service.createInitialAdmin(email, password, clientIp);

            return ResponseEntity.ok("Admin created successfully");

        } catch (IllegalStateException e) {
            // Admin already exists
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Admin already exists");

        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }
}

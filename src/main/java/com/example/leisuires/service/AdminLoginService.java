package com.example.leisuires.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import com.example.leisuires.entity.AdminAccount;
import com.example.leisuires.repository.AdminAccountRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminLoginService {

    private final AdminAccountRepository repository;

    public AdminLoginService(AdminAccountRepository repository) {
        this.repository = repository;
    }

    public void login(String email, String password, HttpSession session) {

        AdminAccount admin = repository.findByEmail(email);

        if (admin == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String hashedInputPassword = hashPassword(password);

        if (!hashedInputPassword.equals(admin.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // ✅ CREATE SESSION
        session.setAttribute("ADMIN_ID", admin.getId());
        session.setAttribute("ADMIN_EMAIL", admin.getEmail());
        session.setAttribute("ADMIN_ROLE", "ADMIN");
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(
                password.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed");
        }
    }
}

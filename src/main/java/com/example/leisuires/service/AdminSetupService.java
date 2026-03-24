package com.example.leisuires.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import com.example.leisuires.entity.AdminAccount;
import com.example.leisuires.repository.AdminAccountRepository;

@Service
public class AdminSetupService {

    private final AdminAccountRepository repository;

    public AdminSetupService(AdminAccountRepository repository) {
        this.repository = repository;
    }

    public void createInitialAdmin(
            String email,
            String password,
            String clientIp) {

        if (!isLocalhost(clientIp)) {
            throw new SecurityException(
                "Admin setup allowed only from localhost"
            );
        }

        if (repository.existsByRole("ADMIN")) {
            throw new IllegalStateException("Admin already exists");
        }

        String hash = hashPassword(password);
        repository.save(new AdminAccount(email, hash));
    }

    private boolean isLocalhost(String ip) {
        return "127.0.0.1".equals(ip) || "::1".equals(ip) || "localhost".equals(ip);
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

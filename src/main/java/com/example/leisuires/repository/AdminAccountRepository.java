package com.example.leisuires.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.leisuires.entity.AdminAccount;

@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {

    boolean existsByRole(String role);
    AdminAccount findByEmail(String email);
}

package com.scb.rwtoolbackend.repository;

import com.scb.rwtoolbackend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // Used for checking if username is already registered (case-insensitive find)
    Admin findByUsernameIgnoreCase(String username);

    // Used for login
    Admin findByUsernameAndPassword(String username, String password);
}

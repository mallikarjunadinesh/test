package com.scb.rwtoolbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins") // Separate table for Admins
@Data
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // NOTE: In a production app, this MUST be hashed (e.g., using BCrypt).

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

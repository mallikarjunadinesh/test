package com.scb.rwtoolbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ops") // Separate table for Ops users
@Data
@NoArgsConstructor
public class Ops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // NOTE: In a production app, this MUST be hashed (e.g., using BCrypt).

    public Ops(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

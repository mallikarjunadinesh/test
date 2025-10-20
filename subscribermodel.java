package com.scb.rwtoolbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscribers") // Separate table for Subscribers
@Data
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // NOTE: In a production app, this MUST be hashed (e.g., using BCrypt).

    public Subscriber(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

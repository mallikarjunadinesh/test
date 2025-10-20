package com.scb.rwtoolbackend.controller;

import com.scb.rwtoolbackend.dto.LoginRequest;
import com.scb.rwtoolbackend.dto.RegistrationRequest;
import com.scb.rwtoolbackend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
// Add CORS to allow your React app (usually on a different port like 3000) to communicate
@CrossOrigin(origins = "http://localhost:3000") 
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        // Validate basic input (can be done more robustly with @Valid annotations)
        if (request.getUsername() == null || request.getPassword() == null || request.getRole() == null) {
            return ResponseEntity.badRequest().body("Username, password, and role are required.");
        }
        
        try {
            boolean success = authService.registerUser(request);
            if (success) {
                return ResponseEntity.ok("Registration successful for role: " + request.getRole());
            } else {
                return ResponseEntity.badRequest().body("Username already exists.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred during registration.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Simple validation
        if (request.getUsername() == null || request.getPassword() == null || request.getRole() == null) {
            return ResponseEntity.badRequest().body("Username, password, and role are required.");
        }

        boolean isAuthenticated = authService.loginUser(request);

        if (isAuthenticated) {
            // A production app would return a JWT token here
            return ResponseEntity.ok(request.getRole()); // Return the role upon success
        } else {
            return ResponseEntity.status(401).body("Invalid credentials or role mismatch.");
        }
    }
}

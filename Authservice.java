package com.scb.rwtoolbackend.service;

import com.scb.rwtoolbackend.dto.LoginRequest;
import com.scb.rwtoolbackend.dto.RegistrationRequest;
import com.scb.rwtoolbackend.model.Admin;
import com.scb.rwtoolbackend.model.Ops;
import com.scb.rwtoolbackend.model.Subscriber;
import com.scb.rwtoolbackend.repository.AdminRepository;
import com.scb.rwtoolbackend.repository.OpsRepository;
import com.scb.rwtoolbackend.repository.SubscriberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final OpsRepository opsRepository;
    private final SubscriberRepository subscriberRepository;

    public AuthService(AdminRepository adminRepository, OpsRepository opsRepository, SubscriberRepository subscriberRepository) {
        this.adminRepository = adminRepository;
        this.opsRepository = opsRepository;
        this.subscriberRepository = subscriberRepository;
    }

    public boolean registerUser(RegistrationRequest request) {
        String role = request.getRole().toLowerCase();

        // 1. Check for existing username across all tables to prevent duplicates
        if (adminRepository.findByUsernameIgnoreCase(request.getUsername()) != null ||
            opsRepository.findByUsernameIgnoreCase(request.getUsername()) != null ||
            subscriberRepository.findByUsernameIgnoreCase(request.getUsername()) != null) {
            return false; // Username already exists
        }

        // 2. Save user to the correct table
        switch (role) {
            case "admin":
                adminRepository.save(new Admin(request.getUsername(), request.getPassword()));
                break;
            case "ops":
                opsRepository.save(new Ops(request.getUsername(), request.getPassword()));
                break;
            case "subscriber":
                subscriberRepository.save(new Subscriber(request.getUsername(), request.getPassword()));
                break;
            default:
                throw new IllegalArgumentException("Invalid role specified.");
        }
        return true;
    }

    public boolean loginUser(LoginRequest request) {
        String role = request.getRole().toLowerCase();

        Object user = null;
        switch (role) {
            case "admin":
                user = adminRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
                break;
            case "ops":
                // Ops can only login if the role selected is 'ops' and credentials are correct
                user = opsRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
                break;
            case "subscriber":
                user = subscriberRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
                break;
            default:
                return false;
        }

        // Authentication successful if a user object was found
        return user != null;
    }
}

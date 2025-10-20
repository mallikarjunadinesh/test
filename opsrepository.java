package com.scb.rwtoolbackend.repository;

import com.scb.rwtoolbackend.model.Ops;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpsRepository extends JpaRepository<Ops, Long> {
    Ops findByUsernameIgnoreCase(String username);
    Ops findByUsernameAndPassword(String username, String password);
}

package com.scb.rwtoolbackend.repository;

import com.scb.rwtoolbackend.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findByUsernameIgnoreCase(String username);
    Subscriber findByUsernameAndPassword(String username, String password);
}

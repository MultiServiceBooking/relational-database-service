package com.example.relationaldatabaseservice.repository;

import com.example.relationaldatabaseservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    Optional<User> findById(Long id);
}

package com.example.relationaldatabaseservice.repository;

import com.example.relationaldatabaseservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

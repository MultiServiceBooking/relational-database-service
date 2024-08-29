package com.example.relationaldatabaseservice.repository;

import com.example.relationaldatabaseservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

package com.example.relationaldatabaseservice.controller;

import com.example.relationaldatabaseservice.dto.PaymentDto;
import com.example.relationaldatabaseservice.model.Payment;
import com.example.relationaldatabaseservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.relationaldatabaseservice.service.PaymentMapper;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    @PostMapping("/{reservationId}")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody Payment payment, @PathVariable String reservationId) {
        Payment createdPayment = paymentService.createPayment(payment, reservationId);
        return ResponseEntity.ok(paymentMapper.toDto(createdPayment));
    }
}

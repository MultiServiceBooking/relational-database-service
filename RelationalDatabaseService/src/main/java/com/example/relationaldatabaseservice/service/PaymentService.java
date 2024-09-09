package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.model.Payment;
import com.example.relationaldatabaseservice.model.Reservation;
import com.example.relationaldatabaseservice.model.User;
import com.example.relationaldatabaseservice.repository.PaymentRepository;
import com.example.relationaldatabaseservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public Payment createPayment(Payment payment, String reservationId) {
        Reservation reservation = reservationRepository.findById(Long.parseLong(reservationId)).orElseThrow(() -> new RuntimeException("User not found"));
        payment.setReservation(reservation);
        payment.setPaymentDate(LocalDate.now());
        return paymentRepository.save(payment);
    }
}

package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.dto.PaymentDto;
import com.example.relationaldatabaseservice.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setPaymentDate(payment.getPaymentDate());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setPaymentStatus(payment.getPaymentStatus());
        paymentDto.setReservationId(payment.getReservation().getId());
        paymentDto.setPaymentMethod(payment.getPaymentMethod());
        paymentDto.setCurrency(payment.getCurrency());
        paymentDto.setConfirmationNumber(payment.getConfirmationNumber());
        paymentDto.setCardLastFourDigits(payment.getCardLastFourDigits());
        return paymentDto;
    }

    public Payment toEntity(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        }

        Payment payment = new Payment();
        payment.setId(paymentDto.getId());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentStatus(paymentDto.getPaymentStatus());
        payment.setReservation(null);
        payment.setPaymentMethod(paymentDto.getPaymentMethod());
        payment.setCurrency(paymentDto.getCurrency());
        payment.setConfirmationNumber(paymentDto.getConfirmationNumber());
        payment.setCardLastFourDigits(paymentDto.getCardLastFourDigits());
        return payment;
    }
}

package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.dto.ReservationDto;
import com.example.relationaldatabaseservice.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {

    public ReservationDto toReservationDTO(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setStartDate(reservation.getStartDate());
        dto.setEndDate(reservation.getEndDate());
        dto.setReservationDate(reservation.getReservationDate());
        dto.setGuestCount(reservation.getGuestCount());
        dto.setReservationStatus(reservation.getReservationStatus());
        dto.setUserId(reservation.getUser().getId());
        dto.setRoomId(reservation.getRoom().getId());
        if (reservation.getPayment() != null) {
            dto.setPaymentId(reservation.getPayment().getId());
        }
        return dto;
    }
}

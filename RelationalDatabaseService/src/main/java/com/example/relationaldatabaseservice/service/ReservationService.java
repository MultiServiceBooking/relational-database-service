package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.enums.ReservationStatus;
import com.example.relationaldatabaseservice.model.Reservation;
import com.example.relationaldatabaseservice.model.Room;
import com.example.relationaldatabaseservice.model.User;
import com.example.relationaldatabaseservice.repository.ReservationRepository;
import com.example.relationaldatabaseservice.repository.RoomRepository;
import com.example.relationaldatabaseservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Reservation createReservation(Long roomId, LocalDate startDate, LocalDate endDate, int guestCount) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));

        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setReservationDate(LocalDate.now()); // Set the current date
        reservation.setGuestCount(guestCount);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservation.setRoom(room);
        reservation.setUser(user);

        return reservationRepository.save(reservation);
    }
}

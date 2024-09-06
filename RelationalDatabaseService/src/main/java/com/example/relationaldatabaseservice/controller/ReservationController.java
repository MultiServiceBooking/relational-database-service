package com.example.relationaldatabaseservice.controller;

import com.example.relationaldatabaseservice.dto.HotelDto;
import com.example.relationaldatabaseservice.dto.ReservationDto;
import com.example.relationaldatabaseservice.model.Hotel;
import com.example.relationaldatabaseservice.model.Reservation;
import com.example.relationaldatabaseservice.service.ReservationMapper;
import com.example.relationaldatabaseservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationMapper reservationMapper;

    @GetMapping("/user/{userId}")
    public List<ReservationDto> getReservationsByUserId(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        return reservations.stream()
                .map(reservationMapper::toReservationDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public Reservation createReservation(
            @RequestParam Long roomId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int guestCount) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return reservationService.createReservation(roomId, start, end, guestCount);
    }

    @GetMapping("/{id}")
    public ReservationDto getById(@PathVariable Long id) {
        Reservation reservation = reservationService.getById(id);
        return reservationMapper.toReservationDTO(reservation);
    }
}

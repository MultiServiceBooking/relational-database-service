package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.model.Room;
import com.example.relationaldatabaseservice.repository.ReservationRepository;
import com.example.relationaldatabaseservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Room> searchAvailableRooms(int guestCount, LocalDate startDate, LocalDate endDate, Long hotelId) {
        // Find all rooms for the specified hotel
        List<Room> allRooms = roomRepository.findByHotelId(hotelId);

        // Find all reservations overlapping with the given dates
        List<Long> reservedRoomIds = reservationRepository.findOverlappingRoomIds(startDate, endDate);

        // Filter rooms based on capacity and reservation status
        return allRooms.stream()
                .filter(room -> room.getCapacity() >= guestCount) // Check capacity
                .filter(room -> !reservedRoomIds.contains(room.getId())) // Check reservation
                .collect(Collectors.toList());
    }
}

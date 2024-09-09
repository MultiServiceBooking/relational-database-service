package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.model.Hotel;
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
        List<Room> allRooms = roomRepository.findByHotelId(hotelId);
        List<Long> reservedRoomIds = reservationRepository.findOverlappingRoomIds(startDate, endDate);
        return allRooms.stream()
                .filter(room -> room.getCapacity() >= guestCount)
                .filter(room -> !reservedRoomIds.contains(room.getId()))
                .collect(Collectors.toList());
    }

    public Room getById(Long roomId)
    {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return room;
    }
}

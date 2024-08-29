package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.dto.RoomDto;
import com.example.relationaldatabaseservice.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.relationaldatabaseservice.model.Hotel;
import com.example.relationaldatabaseservice.repository.HotelRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    public List<RoomDto> getRoomsByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        return hotel.getRooms().stream()
                .map(this::convertRoomToDTO)
                .collect(Collectors.toList());
    }

    private RoomDto convertRoomToDTO(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomType(room.getRoomType());
        dto.setRoomStatus(room.getRoomStatus());
        dto.setPrice(room.getPrice());
        dto.setDescription(room.getDescription());
        return dto;
    }

    public Hotel getById(Long hotelId)
    {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return hotel;
    }
}

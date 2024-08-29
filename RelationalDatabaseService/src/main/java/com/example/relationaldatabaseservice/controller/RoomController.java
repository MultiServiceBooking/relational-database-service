package com.example.relationaldatabaseservice.controller;

import com.example.relationaldatabaseservice.dto.RoomDto;
import com.example.relationaldatabaseservice.model.Hotel;
import com.example.relationaldatabaseservice.model.Room;
import com.example.relationaldatabaseservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/search")
    public List<RoomDto> searchAvailableRooms(
            @RequestParam int guestCount,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam Long hotelId) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Room> availableRooms = roomService.searchAvailableRooms(guestCount, start, end, hotelId);
        return availableRooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RoomDto convertToDTO(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomType(room.getRoomType());
        dto.setRoomStatus(room.getRoomStatus());
        dto.setPrice(room.getPrice());
        dto.setDescription(room.getDescription());
        return dto;
    }
}


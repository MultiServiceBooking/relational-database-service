package com.example.relationaldatabaseservice.controller;

import com.example.relationaldatabaseservice.dto.HotelDto;
import com.example.relationaldatabaseservice.dto.RoomDto;
import com.example.relationaldatabaseservice.model.Hotel;
import com.example.relationaldatabaseservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<HotelDto> getAllHotels() {
        List<Hotel> hotels = hotelService.getAll();
        return hotels.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private HotelDto convertToDTO(Hotel hotel) {
        HotelDto dto = new HotelDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setAddress(hotel.getAddress());
        dto.setStarRating(hotel.getStarRating());
        dto.setPhoneNumber(hotel.getPhoneNumber());
        dto.setEmail(hotel.getEmail());
        return dto;
    }

    @GetMapping("/{id}/rooms")
    public List<RoomDto> getRoomsByHotelId(@PathVariable Long id) {
        return hotelService.getRoomsByHotelId(id);
    }

    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getById(id);
        return convertToDTO(hotel);
    }
}


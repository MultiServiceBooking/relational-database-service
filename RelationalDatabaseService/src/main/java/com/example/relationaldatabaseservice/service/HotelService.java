package com.example.relationaldatabaseservice.service;

import com.example.relationaldatabaseservice.dto.RoomDto;
import com.example.relationaldatabaseservice.model.Reservation;
import com.example.relationaldatabaseservice.model.Room;
import com.example.relationaldatabaseservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.example.relationaldatabaseservice.model.Hotel;
import com.example.relationaldatabaseservice.repository.HotelRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Hotel> searchHotels(String address, LocalDate startDate, LocalDate endDate, int numberOfGuests) {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream()
                .filter(hotel -> {
                    boolean addressMatch = containsAddress(hotel, address);
                    boolean capacityMatch = haveCapacity(hotel, numberOfGuests);
                    boolean roomAvailable = isAvailableRoom(hotel, startDate, endDate);
                    return addressMatch && capacityMatch && roomAvailable;
                })
                .collect(Collectors.toList());
    }


    private boolean containsAddress(Hotel hotel, String address) {
        if (address == null || address.isEmpty()) {
            System.out.println("Address is null or empty, returning true");
            return true;
        }
        boolean contains = hotel.getAddress().toLowerCase().contains(address.toLowerCase());
        return contains;
    }


    private boolean haveCapacity(Hotel hotel, int numberOfGuests) {
        if (numberOfGuests > 0) {
            return hotel.getRooms().stream()
                    .anyMatch(room -> room.getCapacity() >= numberOfGuests);
        }
        return true;
    }

    private boolean isAvailableRoom(Hotel hotel, LocalDate startDate, LocalDate endDate) {
        List<Long> reservedRoomIds = reservationRepository.findOverlappingRoomIds(startDate, endDate);
        return hotel.getRooms().stream()
                .anyMatch(room -> !reservedRoomIds.contains(room.getId()));
    }


    private boolean hasOverlappingReservations(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.findByRoomIdAndStartDateBeforeAndEndDateAfter(
                roomId, endDate, startDate);

        return !reservations.isEmpty();
    }

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
        dto.setImage(room.getImage());
        return dto;
    }

    public Hotel getById(Long hotelId)
    {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return hotel;
    }


}

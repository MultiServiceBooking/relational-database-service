package com.example.relationaldatabaseservice.repository;

import com.example.relationaldatabaseservice.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}

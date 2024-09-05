package com.example.relationaldatabaseservice.repository;

import com.example.relationaldatabaseservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT DISTINCT r.room.id FROM Reservation r WHERE " +
            "((r.startDate <= :endDate AND r.endDate >= :startDate))")
    List<Long> findOverlappingRoomIds(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
    List<Reservation> findByRoomIdAndStartDateBeforeAndEndDateAfter(Long roomId, LocalDate endDate, LocalDate startDate);
    List<Reservation> findByUserId(Long userId);
}

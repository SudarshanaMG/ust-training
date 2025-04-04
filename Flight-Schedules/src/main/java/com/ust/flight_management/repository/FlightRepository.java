package com.ust.flight_management.repository;



import com.ust.flight_management.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findBySourceAndDestinationAndDepartureDateTimeBetween(
            String source, String destination, LocalDateTime startDate, LocalDateTime endDate);
}

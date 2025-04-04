package com.ust.flight_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightResponseDTO {
    private String flightName;
    private String sourceName;
    private String destinationName;
    private String aircraftType;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double totalFare;

}


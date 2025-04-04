package com.ust.flight_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightEvent {
    private String flightNumber;
    private String source;
    private String destination;
    private String departureTime;
    private String status;

}

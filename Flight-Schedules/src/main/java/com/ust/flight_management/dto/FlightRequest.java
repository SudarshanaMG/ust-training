package com.ust.flight_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightRequest {
    private String flightNumber;
    private String source;
    private String destination;
    private String departureTime;

}

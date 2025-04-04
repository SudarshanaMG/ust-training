package com.ust.flight_management.service;


import com.ust.flight_management.dto.FlightResponseDTO;
import com.ust.flight_management.entity.Flight;
import com.ust.flight_management.repository.FlightRepository;
//package com.airport.flightschedule.service;
//
//import com.airport.flightschedule.entity.Flight;
//import com.airport.flightschedule.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<FlightResponseDTO> getFlights(String source, String destination, LocalDate departureDate) {
        LocalDateTime startOfDay = departureDate.atStartOfDay();  // 00:00:00
        LocalDateTime endOfDay = departureDate.atTime(LocalTime.MAX);  // 23:59:59

        List<Flight> flights = flightRepository.findBySourceAndDestinationAndDepartureDateTimeBetween(
                source, destination, startOfDay, endOfDay);

        return flights.stream().map(flight -> new FlightResponseDTO(
                flight.getAirline(),
                flight.getSourceName(),
                flight.getDestinationName(),
                flight.getAircraftType(),
                flight.getDepartureDateTime(),
                flight.getArrivalDateTime(),
                flight.getTotalFare()
        )).collect(Collectors.toList());
    }

    public void saveAll(List<Flight> flights){
        flightRepository.saveAll(flights);
    }
}

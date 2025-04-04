package com.ust.flight_management.controller;
import com.ust.flight_management.dto.FlightResponseDTO;
import com.ust.flight_management.entity.Flight;
import com.ust.flight_management.service.CsvService;
import com.ust.flight_management.service.FlightService;
//package com.airport.flightschedule.controller;

//import com.airport.flightschedule.entity.Flight;
//import com.airport.flightschedule.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private CsvService csvService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        List<Flight> flights = csvService.parseCsv(file);
        flightService.saveAll(flights);
        return ResponseEntity.ok("CSV Uploaded Successfully!");
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightResponseDTO>> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {

        List<FlightResponseDTO> flights = flightService.getFlights(source, destination, departureDate);

        if (flights.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no flights found
        }

        return ResponseEntity.ok(flights); // Return list of filtered flight data
    }
}

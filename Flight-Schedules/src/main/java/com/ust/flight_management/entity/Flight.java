package com.ust.flight_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import lombok.Data;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "flight_details")
//@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "airline")
    private String airline;
    @Column(name = "source")// Airline Name
    private String source;
    @Column(name = "Source Name")// Source Airport Code (e.g., CXB)
    private String sourceName;
    @Column(name = "destination")// Source Airport Full Name
    private String destination;
    @Column(name = "Destination Name")// Destination Airport Code (e.g., CCU)
    private String destinationName;
    @Column(name = "Departure Date & Time")// Destination Airport Full Name
    private LocalDateTime departureDateTime;
    @Column(name = "Arrival Date & Time")// Departure Date & Time
    private LocalDateTime arrivalDateTime;
    @Column(name = "Duration (hrs)")// Arrival Date & Time
    private Double duration;
    @Column(name = "stopovers")// Duration in Hours
    private String stopovers;
    @Column(name = "Aircraft Type")// Direct or 1 Stop, etc.
    private String aircraftType;
    @Column(name = "Class")// Aircraft Type (e.g., Airbus A320)
    private String flightClass;
    @Column(name = "Booking Source")// Economy, First Class, etc.
    private String bookingSource;
    @Column(name = "Base Fare (BDT)")// Online Website, Travel Agency, etc.
    private Double baseFare;
    @Column(name = "Tax & Surcharge (BDT)")// Base Fare in BDT
    private Double taxAndSurcharge;
    @Column(name = "Total Fare (BDT)")// Tax & Surcharge in BDT
    private Double totalFare;
    @Column(name = "seasonality")// Total Fare in BDT
    private String seasonality;
    @Column(name = "Days Before Departure")// Regular, Winter Holidays, etc.
    private Integer daysBeforeDeparture;
    // Number of days before departure

}

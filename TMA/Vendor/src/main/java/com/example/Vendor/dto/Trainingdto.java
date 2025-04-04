package com.example.Vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainingdto {
    private Long trainingId;
    private String trainingName;
    private List<String> skills;
    private String duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private long noOfBatches;
    private long noOfStudentsPerBatch;
    private String organization;
    private double budget;
    private String email;
    private String phone;
    private String toc;
    private String poc;
    private String status;
}


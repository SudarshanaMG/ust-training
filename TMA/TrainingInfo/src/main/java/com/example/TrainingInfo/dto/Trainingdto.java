package com.example.TrainingInfo.dto;

import com.example.TrainingInfo.model.TrainingIn;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainingdto {
    private Long id;
    private String name;
    private List<String> skills;
    private TrainingIn.DurationType duration;
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
    private TrainingIn.TrainingStatus status;

    public Trainingdto(Long id,String name, String organization, LocalDate startDate, LocalDate endDate, List<String> skills){

    }
}

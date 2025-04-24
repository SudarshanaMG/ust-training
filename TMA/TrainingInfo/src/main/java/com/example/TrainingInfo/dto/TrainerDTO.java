package com.example.TrainingInfo.dto;

import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainerDTO {
    private Long trainerId;
    private String trainerName;
    private double minBudget;
    private List<String> expertise;
    private double rating;
}

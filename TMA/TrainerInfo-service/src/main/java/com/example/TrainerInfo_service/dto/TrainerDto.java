package com.example.TrainerInfo_service.dto;

import com.example.TrainerInfo_service.model.TrainerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainerDto {
    private Long trainerId;

    private String trainerName;

    private String email;

    private String phone;
    private List<String> expertise;
    private int experience;
    //private Long vendorId;
    private Long trainingId;
    private TrainerInfo.Status status;
    private double rating;
    private double minBudget;

    public TrainerDto(Long trainerId, String trainerName, double minBudget, List<String> expertise) {
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.minBudget = minBudget;
        this.expertise = expertise;
    }

    public enum Status{
        AVAILABLE,NOT_AVAILABLE
    }
    public BankDetailsDto bankDetails;

}

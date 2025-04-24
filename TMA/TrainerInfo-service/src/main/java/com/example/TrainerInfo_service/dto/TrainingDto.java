package com.example.TrainerInfo_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TrainingDto {
    private Long trainingId;
    private String trainingName;
    private List<String> skills;
    private DurationType duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private long noOfBatches;
    private long noOfStudentsPerBatch;
    private double budget;
    private String email;
    private String phone;
    private String toc;
    private String poc;
    private StudentType studentType;
    private TrainingStatus status;
    private TrainingType type;
    private Long trainerId;
    private Long vendorId;
    public enum TrainingType {
        ONLINE, OFFLINE
    }

    public enum DurationType {
        HOURS, DAYS, MONTHS
    }

    public enum StudentType {
        FRESHER, LATERAL
    }

    public enum TrainingStatus {
        ON_HOLD, IN_PROGRESS, COMPLETED, UPCOMING
    }
}

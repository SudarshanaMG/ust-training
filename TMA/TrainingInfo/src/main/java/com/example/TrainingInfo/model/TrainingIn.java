package com.example.TrainingInfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "traininginfo")
public class TrainingIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Training name is required")
    private String name;

    @ElementCollection
    @NotEmpty(message = "At least one skill is required")
    private List<String> skills;

    @Enumerated(EnumType.STRING)
    private DurationType duration;

    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Min(value = 1, message = "Number of batches must be at least 1")
    private long noOfBatches;

    @Min(value = 1, message = "Number of students per batch must be at least 1")
    private long noOfStudentsPerBatch;

    @NotBlank(message = "Organization name is required")
    private String organization;

    @Positive(message = "Budget must be positive")
    private double budget;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Table of contents (TOC) link is required")
    private String toc;

    @NotBlank(message = "Point of contact (POC) is required")
    private String poc;
    private Long trainerId;

    @Enumerated(EnumType.STRING)
    private TrainingStatus status;

    public enum DurationType {
        HOURS, DAYS, MONTHS
    }

    public enum TrainingStatus {
        ON_HOLD, IN_PROGRESS, COMPLETED, UPCOMING
    }

}


package com.example.TrainerInfo_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trainerId;

    private String trainerName;

    private String email;

    private String phone;
    @ElementCollection
    private List<String> expertise;
    private int experience;
    //private Long vendorId;
    private Long trainingId;
    @Enumerated(EnumType.STRING)
    private Status status;

    private double rating;
    private double minBudget;
    public enum Status{
        AVAILABLE,NOT_AVAILABLE
    }
    @Embedded
    public BankDetails bankDetails;

}
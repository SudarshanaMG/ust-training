package com.example.TrainerInfo_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="trainerinfo")
public class TrainerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainerId;
    private String name;
    private String email;
    private String phoneNumber;
    private int experience;
    @ElementCollection
    private List<String> skills;
    private int budget;
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status{
        AVAILABLE, NOTAVAILABLE
    }

}
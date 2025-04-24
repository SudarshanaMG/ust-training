package com.example.Vendor.dto;

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
    private String email;
    private String phone;
    private List<String> expertise;
    private int experience;
    private double rating;
}

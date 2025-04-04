package com.example.Vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainerdto {
    private long trainerId;
    private String name;
    private String email;
    private String phoneNumber;
    private int experience;
    private List<String> skills;
    private int budget;
    private String status;
}

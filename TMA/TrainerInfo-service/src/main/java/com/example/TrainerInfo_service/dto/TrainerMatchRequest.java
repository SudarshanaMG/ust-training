package com.example.TrainerInfo_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainerMatchRequest {
    private List<String> skills;
    private double budget;
}

package com.example.TrainerInfo_service.dto;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseDto {
    private TrainerDto trainer;
    private List<TrainingDto> trainingList;
}

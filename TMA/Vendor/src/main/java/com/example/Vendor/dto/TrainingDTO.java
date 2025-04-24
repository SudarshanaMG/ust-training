package com.example.Vendor.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainingDTO {
    private Long trainingId;
    private String trainingName;
    private String duration;
    private String startDate;
    private String endDate;
    private Double budget;
}


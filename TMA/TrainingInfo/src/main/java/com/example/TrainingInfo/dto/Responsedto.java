package com.example.TrainingInfo.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Responsedto {
    private String name;
    private String orgName;
    private Long id;
    private String startDate;
    private String endDate;
    private List<String> skills;
}

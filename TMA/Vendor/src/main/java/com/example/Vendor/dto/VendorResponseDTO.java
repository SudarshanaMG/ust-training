package com.example.Vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendorResponseDTO {
    private VendorDTO vendorsDto;
    private List<TrainingWithTrainersDTO> trainingsFromParticularVendor;
}

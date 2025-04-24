package com.example.TrainerInfo_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankDetailsDto {
    private String bankingName;
    private Long accountNumber;
    private String IFSC;
}

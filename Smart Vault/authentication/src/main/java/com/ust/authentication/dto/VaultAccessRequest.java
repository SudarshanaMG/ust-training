package com.ust.authentication.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VaultAccessRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 15, max = 15, message = "Account number must be exactly 15 digits")
    @Pattern(regexp = "^[0-9]{15}$", message = "Account number must contain only digits")
    private String accNumber;

    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be exactly 16 digits")
    private String cardNumber;

    @Pattern(regexp = "^[0-9]{4,6}$", message = "PIN must be 4 to 6 digits")
    private String pin;

    @NotNull
    private Long VaultId;

}

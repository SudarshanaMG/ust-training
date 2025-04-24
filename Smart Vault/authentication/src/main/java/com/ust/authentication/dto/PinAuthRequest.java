package com.ust.authentication.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PinAuthRequest {

    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be exactly 16 digits")
    private String cardNumber;

    @Pattern(regexp = "^[0-9]{4,6}$", message = "PIN must be 4 to 6 digits")
    private String pin;
}
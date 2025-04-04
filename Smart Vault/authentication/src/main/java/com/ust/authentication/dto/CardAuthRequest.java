package com.ust.authentication.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CardAuthRequest {
    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be exactly 16 digits")
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private AuthUser.Card cardType;

    @Min(value = 100, message = "CVV must be at least 3 digits")
    @Max(value = 9999, message = "CVV must be at most 4 digits")
    private int cardCvv;

    @Pattern(regexp = "^[0-9]{4,6}$", message = "PIN must be 4 to 6 digits")
    private String pin;
}
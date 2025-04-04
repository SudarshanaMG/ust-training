package com.ust.userInfo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "UserInfo")
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @Size(min = 12, max = 12, message = "Aadhar must be exactly 12 digits")
    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar must contain only digits")
    private String aadhar;

    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$", message = "Invalid PAN format")
    private String pan;

    @Size(min = 15, max = 15, message = "Account number must be exactly 15 digits")
    @Pattern(regexp = "^[0-9]{15}$", message = "Account number must contain only digits")
    private String accNumber;

    @Enumerated(EnumType.STRING)
    private Acc accType;

    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must be exactly 16 digits")
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private Card cardType;

    @Min(value = 100, message = "CVV must be at least 3 digits")
    @Max(value = 9999, message = "CVV must be at most 4 digits")
    private int cardCvv;

    @Pattern(regexp = "^[0-9]{4,6}$", message = "PIN must be 4 to 6 digits")
    private String pin;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Vault> vaults;

    public enum Acc {
        SAVINGS, CURRENT
    }

    public enum Card {
        CREDIT, DEBIT
    }

}

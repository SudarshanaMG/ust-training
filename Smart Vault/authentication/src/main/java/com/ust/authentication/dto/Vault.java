package com.ust.authentication.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Vault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaultNumber;
    private String storedItem;  // Item stored in the vault
    private String status;  // LOCKED or UNLOCKED
    private String userId;

}


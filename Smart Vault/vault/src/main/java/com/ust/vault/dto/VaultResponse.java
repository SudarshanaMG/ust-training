package com.ust.vault.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VaultResponse {
    private Long id;

    private String vaultNumber;
    private String storedItem;  // Item stored in the vault
    private String status;  // LOCKED or UNLOCKED
    private Long userId;
}

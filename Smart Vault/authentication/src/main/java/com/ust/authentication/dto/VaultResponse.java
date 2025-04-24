package com.ust.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

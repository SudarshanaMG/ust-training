package com.ust.vault;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Vault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaultNumber;
    private String storedItem;  // Item stored in the vault
    private String status;  // LOCKED or UNLOCKED
    private List<Long> userId;
}


package com.ust.userInfo.controller;

import com.ust.userInfo.model.AuthUser;
import com.ust.userInfo.model.Vault;
import com.ust.userInfo.repository.AuthUserRepository;
import com.ust.userInfo.repository.VaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vaults")
public class VaultController {
    @Autowired
    private VaultRepository vaultRepository;
    @Autowired
    private AuthUserRepository authUserRepository;


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserVaults(@PathVariable Long userId) {
        List<Vault> vaults = vaultRepository.findByUserId(userId);
        if(vaults.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<Long> vaultIds = new java.util.ArrayList<>(List.of());
        for (Vault vault : vaults) {
            vaultIds.add(vault.getId());
        }
        return ResponseEntity.ok(vaultIds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vault> getVaultById(@PathVariable Long id) {
        return vaultRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVault(@RequestBody Vault vault) {
        vault.setStatus("LOCKED");
        Vault savedVault = vaultRepository.save(vault);
        return ResponseEntity.ok(savedVault);
    }

    @PutMapping("/link/{vaultId}/user/{userId}")
    public ResponseEntity<String> linkVaultToUser(@PathVariable Long vaultId, @PathVariable Long userId) {
        Optional<Vault> vaultOpt = vaultRepository.findById(vaultId);
        Optional<AuthUser> user = authUserRepository.findById(userId);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (vaultOpt.isPresent()) {
            Vault vault = vaultOpt.get();
            if(vault.getUserId() != null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vault is being used , cannot be reassigned.");
            }
            vault.setUserId(userId);  // Store only user ID
            vaultRepository.save(vault);
            return ResponseEntity.ok("Vault linked to user successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vault not found.");
    }


}


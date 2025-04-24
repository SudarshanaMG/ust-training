package com.ust.vault.controller;

import com.ust.vault.AuthClient;
import com.ust.vault.VaultClient;
import com.ust.vault.dto.AuthResponse;
import com.ust.vault.dto.PinAuthRequest;
import com.ust.vault.dto.VaultAccessRequest;
import com.ust.vault.dto.VaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/vaults")
public class VaultController {
        @Autowired
        private AuthClient authClient;
        @Autowired
        private VaultClient vaultClient;

        @PostMapping("/access")
        public ResponseEntity<?> accessVault(@RequestHeader("Authorization") String token, @RequestBody VaultAccessRequest request) {
            ResponseEntity<AuthResponse> response = authClient.authenticatePin(token,
                    new PinAuthRequest(request.getCardNumber(), request.getPin()));

            if (response.getStatusCode() == HttpStatus.OK) {
                if(Objects.requireNonNull(response.getBody()).getVaults().contains(request.getVaultId())){
                    ResponseEntity<VaultResponse> res = vaultClient.getVault(request.getVaultId());
                    return ResponseEntity.ok().body(Objects.requireNonNull(res.getBody()).getStoredItem());
                }
                return ResponseEntity.ok("specified vault is not registered with this user");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vault Access Denied");
        }
}


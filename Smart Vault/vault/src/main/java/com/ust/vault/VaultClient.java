package com.ust.vault;

import com.ust.vault.dto.AuthResponse;
import com.ust.vault.dto.PinAuthRequest;
import com.ust.vault.dto.VaultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-info-service", url = "http://localhost:2020")
public interface VaultClient {
    @GetMapping("/vaults/{id}")
    ResponseEntity<VaultResponse> getVault(@PathVariable Long id);
}

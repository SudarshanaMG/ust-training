package com.ust.authentication;

import com.ust.authentication.dto.AuthUser;
import com.ust.authentication.dto.Vault;
import com.ust.authentication.dto.VaultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-info-service", url = "http://localhost:2020")
public interface UserClient {
    @GetMapping("/user/card/{cardNumber}")
    public AuthUser getUserByCard(@PathVariable String cardNumber);
    @GetMapping("/vaults/user/{userId}")
    List<Long> getUserVaults(@PathVariable Long userId);
    @GetMapping("/vaults/{id}")
    ResponseEntity<VaultResponse> getVault(@PathVariable Long id);
}

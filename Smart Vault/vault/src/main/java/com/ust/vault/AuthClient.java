package com.ust.vault;

import com.ust.vault.dto.AuthResponse;
import com.ust.vault.dto.PinAuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authentication-service", url = "http://localhost:2021")
public interface AuthClient {
    @PostMapping("/auth/pin")
    ResponseEntity<AuthResponse> authenticatePin(@RequestBody PinAuthRequest request);
}


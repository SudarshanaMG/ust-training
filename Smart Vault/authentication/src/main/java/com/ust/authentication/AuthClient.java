package com.ust.authentication;

import com.ust.authentication.dto.AuthResponse;
import com.ust.authentication.dto.PinAuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "authentication-service",
        url = "http://localhost:2021"
)
public interface AuthClient {
    @PostMapping("/auth/pin")
    ResponseEntity<AuthResponse> authenticatePin(@RequestBody PinAuthRequest request);
}


package com.ust.authentication.controller;

import com.ust.authentication.UserClient;
//import com.ust.authentication.VaultClient;
import com.ust.authentication.config.JwtUtil;
import com.ust.authentication.dto.AuthResponse;
import com.ust.authentication.dto.AuthUser;
import com.ust.authentication.dto.CardAuthRequest;
import com.ust.authentication.dto.PinAuthRequest;
import com.ust.authentication.dto.Vault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserClient userClient;

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/card")
    public ResponseEntity<?> authenticateCard(@RequestBody CardAuthRequest request) {
        AuthUser user = userClient.getUserByCard(request.getCardNumber());
        if (user != null && user.getCardType().equals(request.getCardType())) {
            String token = jwtUtil.generateToken(request.getCardNumber());
            return ResponseEntity.ok("Thank you, Card Verified - you can enter : " + token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Card Details");
    }

    @PostMapping("/pin")
    public ResponseEntity<?> authenticatePin(@RequestBody PinAuthRequest request) {
        AuthUser user = userClient.getUserByCard(request.getCardNumber());

        if (user != null && (Objects.equals(request.getPin(), user.getPin()))) {
            List<Long> vaults = userClient.getUserVaults(user.getId());
            return ResponseEntity.ok(new AuthResponse(user.getId(), user.getName(), "PIN Verified", vaults));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect PIN");
    }
}


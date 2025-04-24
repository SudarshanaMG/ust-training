package com.ust.authentication.controller;

import com.ust.authentication.AuthClient;
import com.ust.authentication.UserClient;
//import com.ust.authentication.VaultClient;
import com.ust.authentication.config.JwtUtil;
import com.ust.authentication.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserClient userClient;
    @Autowired
    private AuthClient authClient;

    private final JwtUtil jwtUtil;
//    @Autowired
//    private VaultClient vaultClient;

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

//    @PostMapping("/pin")
//    public ResponseEntity<?> authenticatePin(@RequestBody PinAuthRequest request) {
//        AuthUser user = userClient.getUserByCard(request.getCardNumber());
//
//        if (user != null && (Objects.equals(request.getPin(), user.getPin()))) {
//            List<Long> vaults = userClient.getUserVaults(user.getId());
//            return ResponseEntity.ok(new AuthResponse(user.getId(), user.getName(), "PIN Verified", vaults));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect PIN");
//    }

    @Operation(
            summary = "Verify PIN",
            description = "Requires valid JWT token from /auth/card",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PIN verified and vaults returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Incorrect PIN", content = @Content)
    })
    @PostMapping("/pin")
    public ResponseEntity<?> authenticatePin(@RequestBody PinAuthRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String cardNumber;

        if (principal instanceof UserDetails) {
            cardNumber = ((UserDetails) principal).getUsername(); // this is the card number
        } else {
            cardNumber = principal.toString();
        }

        AuthUser user = userClient.getUserByCard(cardNumber);

        if (user != null && Objects.equals(request.getPin(), user.getPin())) {
            List<Long> vaults = userClient.getUserVaults(user.getId());
            return ResponseEntity.ok(new AuthResponse(user.getId(), user.getName(), "PIN Verified", vaults));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect PIN");
    }

    @PostMapping("/access")
    public ResponseEntity<?> accessVault(@RequestBody VaultAccessRequest request) {
//        ResponseEntity<AuthResponse> response = authClient.authenticatePin(
//                new PinAuthRequest(request.getCardNumber(), request.getPin()));

//        if (response.getStatusCode() == HttpStatus.OK) {
//            if(Objects.requireNonNull(response.getBody()).getVaults().contains(request.getVaultId())){
//                ResponseEntity<VaultResponse> res = userClient.getVault(request.getVaultId());
//                return ResponseEntity.ok().body(Objects.requireNonNull(res.getBody()).getStoredItem());
//            }
//            return ResponseEntity.ok("specified vault is not registered with this user");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vault Access Denied");
        List<Long> response = userClient.getUserVaults(request.getId());
        if(!response.isEmpty()){
            if(response.contains(request.getVaultId())){
                ResponseEntity<VaultResponse> res = userClient.getVault(request.getVaultId());
                return ResponseEntity.ok().body(Objects.requireNonNull(res.getBody()).getStoredItem());
            }
            return ResponseEntity.ok("Specified vault is not registered with this user");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vault Access Denied");
    }

}


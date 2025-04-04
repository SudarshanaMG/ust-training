package com.ust.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String userName;
    private String message;
    private List<Long> vaults;

}
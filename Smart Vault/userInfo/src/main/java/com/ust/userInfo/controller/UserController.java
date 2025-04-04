package com.ust.userInfo.controller;

import com.ust.userInfo.model.AuthUser;
import com.ust.userInfo.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthUserService userService;

    @PostMapping
    public ResponseEntity<AuthUser> addTrainer(@RequestBody AuthUser user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping
    public ResponseEntity<List<AuthUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("card/{cardNumber}")
    public ResponseEntity<AuthUser> getUserByCardNumber(@PathVariable String cardNumber) {
        return userService.getUserByCardNumber(cardNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

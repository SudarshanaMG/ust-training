package com.ust.userInfo.repository;

import com.ust.userInfo.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    public Optional<AuthUser> findByCardNumber(String cardNumber);
}

package com.ust.userInfo.repository;

import com.ust.userInfo.model.Vault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {
    List<Vault> findByUserId(Long userId);
}
package com.ust.vault.repository;

import com.ust.vault.Vault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {

}


package com.example.Vendor.repository;

import com.example.Vendor.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, String> {
    List<Vendor> findByHeadOfficeLocation(String headOfficeLocation);
}


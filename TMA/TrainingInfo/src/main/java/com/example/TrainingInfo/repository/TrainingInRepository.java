package com.example.TrainingInfo.repository;

import com.example.TrainingInfo.model.TrainingIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingInRepository extends JpaRepository<TrainingIn, Long> {
    int countByTrainerId(Long trainerId);
    List<TrainingIn> findByTrainerId(Long trainerId);
    List<TrainingIn> findByVendorId(Long vendorId);
}
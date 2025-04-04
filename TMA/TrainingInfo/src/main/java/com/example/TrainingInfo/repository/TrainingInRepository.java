package com.example.TrainingInfo.repository;

import com.example.TrainingInfo.model.TrainingIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingInRepository extends JpaRepository<TrainingIn, Long> {
    List<TrainingIn> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    List<TrainingIn> findByStartDate(LocalDate startDate);
//    @Query("SELECT t FROM Training t JOIN t.skills s WHERE s IN :skills")
//    List<TrainingIn> findBySkills(List<String> skills);
    List<TrainingIn> findByOrganization(String organization);
}
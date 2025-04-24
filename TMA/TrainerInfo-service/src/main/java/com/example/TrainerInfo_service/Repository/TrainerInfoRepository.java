package com.example.TrainerInfo_service.Repository;

import com.example.TrainerInfo_service.model.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerInfoRepository extends JpaRepository<TrainerInfo, Long> {
    List<TrainerInfo> findByTrainingId(Long trainingId);
    List<TrainerInfo> findByStatusAndExpertise(TrainerInfo.Status status, List<String> expertise);
//    @Query("SELECT DISTINCT t FROM Trainer t JOIN t.expertise e " +
//            "WHERE t.status = 'AVAILABLE' AND t.minBudget <= :maxBudget AND e IN (:skills)")
//    List<TrainerInfo> findAvailableTrainersByExpertiseAndBudget(@Param("skills") List<String> skills,
//                                                            @Param("maxBudget") double maxBudget);

//    @Query("SELECT t FROM TrainerInfo t JOIN t.expertise e WHERE t.status = :status AND e IN :expertise")
//    List<TrainerInfo> findAvailableTrainersByExpertise(@Param("status") TrainerInfo.Status status,
//                                                       @Param("expertise") List<String> expertise);

//
//    @Query("SELECT t FROM Trainer t " +
//            "WHERE t.status = 'AVAILABLE' " +
//            "AND LOWER(t.expertise) IN :expertiseList " +
//            "AND t.minBudget <= :maxBudget " +
//            "ORDER BY t.rating DESC")
//    List<TrainerInfo> findEligibleTrainers(@Param("expertiseList") List<String> expertiseList,
//                                       @Param("maxBudget") Double maxBudget);
//@Query("SELECT t FROM Trainer t WHERE :skill MEMBER OF t.expertise AND t.minBudget <= :budget")
//List<TrainerInfo> findEligibleTrainers(@Param("skill") List<String> skill, @Param("budget") Double budget);

//    @Query("SELECT t FROM Trainer t " +
//            "WHERE t.status = 'AVAILABLE' " +
//            "AND ( " +
//            "   LOWER(t.expertise) LIKE LOWER(CONCAT('%', :skill, '%')) " +
//            ") " +
//            "AND t.minBudget <= :maxBudget " +
//            "ORDER BY t.rating DESC")
//    List<TrainerInfo> findEligibleTrainers(@Param("skill") String skill,
//                                       @Param("maxBudget") Double maxBudget);

//    @Query("SELECT t FROM Trainer t WHERE t.status = 'AVAILABLE' AND EXISTS " +
//        "(SELECT skill FROM t.expertise skill WHERE skill IN :skills)")
//List<TrainerInfo> findAvailableTrainersByExpertise(@Param("skills") List<String> skills);
}

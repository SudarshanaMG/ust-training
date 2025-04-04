package com.example.TrainerInfo_service.Repository;

import com.example.TrainerInfo_service.model.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrainerInfoRepository extends JpaRepository<TrainerInfo, Long> {
    List<TrainerInfo> findBySkillsContaining(String skills);
    List<TrainerInfo> findByBudget(int budget);
    List<TrainerInfo> findByBudgetLessThanEqualAndSkillsInAndStatus(int budget, List<String> skills, TrainerInfo.Status status);
    List<TrainerInfo> findByBudgetAndSkillsAndStatus(int budget, String skills, TrainerInfo.Status status);
//    List<TrainerInfo> countByTrainerIdAndStatus(Long trainerId, TrainerInfo.Status status);
}

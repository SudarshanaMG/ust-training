package com.example.TrainerInfo_service.Service;

import com.example.TrainerInfo_service.Repository.TrainerInfoRepository;
import com.example.TrainerInfo_service.model.TrainerInfo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerInfoService {
    private final TrainerInfoRepository trainerInfoRepository;

    public TrainerInfoService(TrainerInfoRepository trainerInfoRepository) {
        this.trainerInfoRepository = trainerInfoRepository;
    }

    public TrainerInfo addTrainer(TrainerInfo trainerInfo) {
        return trainerInfoRepository.save(trainerInfo);
    }

    public List<TrainerInfo> getAllTrainers() {
        return trainerInfoRepository.findAll();
    }

    public Optional<TrainerInfo> getTrainerById(Long id) {
        return trainerInfoRepository.findById(id);
    }

    public List<TrainerInfo> getTrainersBySkill(String skills) {
        return trainerInfoRepository.findBySkillsContaining(skills);
    }

    public List<TrainerInfo> getTrainersByBudget(int budget) {
        return trainerInfoRepository.findByBudget(budget);
    }

    public List<TrainerInfo> findMatchingTrainers(int budget, List<String> skills, TrainerInfo.Status status) {
        return trainerInfoRepository.findByBudgetLessThanEqualAndSkillsInAndStatus(budget, skills, status);
    }

    public List<TrainerInfo> getTrainersByBudgetAndSkills(int budget, String skills, TrainerInfo.Status status) {
        return trainerInfoRepository.findByBudgetAndSkillsAndStatus(budget, skills, status);
    }

//    public boolean isTrainerAvailable(Long trainerId, TrainerInfo.Status status){
//        return trainerInfoRepository.countByTrainerIdAndStatus(trainerId, status).isEmpty();
//    }

    public TrainerInfo updateTrainer(Long id, TrainerInfo updatedTrainer) {
        return trainerInfoRepository.findById(id)
                .map(trainer -> {
                    trainer.setName(updatedTrainer.getName());
                    trainer.setEmail(updatedTrainer.getEmail());
                    trainer.setPhoneNumber(updatedTrainer.getPhoneNumber());
                    trainer.setExperience(updatedTrainer.getExperience());
                    trainer.setSkills(updatedTrainer.getSkills());
                    return trainerInfoRepository.save(trainer);
                }).orElse(null);
    }

    public void deleteTrainer(Long id) {
        trainerInfoRepository.deleteById(id);
    }
}


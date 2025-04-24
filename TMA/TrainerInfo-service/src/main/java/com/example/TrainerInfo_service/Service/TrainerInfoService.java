package com.example.TrainerInfo_service.Service;


import com.example.TrainerInfo_service.Repository.TrainerInfoRepository;
import com.example.TrainerInfo_service.dto.ResponseDto;
import com.example.TrainerInfo_service.dto.TrainerDto;
import com.example.TrainerInfo_service.dto.TrainingDto;
import com.example.TrainerInfo_service.dto.TrainingIDDto;
import com.example.TrainerInfo_service.model.TrainerInfo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerInfoService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerInfoService.class);

    @Autowired
    private TrainerInfoRepository trainerRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public TrainerInfo createTrainer(TrainerInfo trainer) {
        return trainerRepository.save(trainer);
    }

    public List<TrainerInfo> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public TrainerInfo getTrainerById(Long id) throws ChangeSetPersister.NotFoundException {
        return trainerRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public TrainerInfo updateTrainer(Long id, TrainerInfo trainerDetails) throws ChangeSetPersister.NotFoundException {
        TrainerInfo trainer = getTrainerById(id);

        trainer.setTrainerName(trainerDetails.getTrainerName());
        trainer.setEmail(trainerDetails.getEmail());
        trainer.setPhone(trainerDetails.getPhone());
        trainer.setExpertise(trainerDetails.getExpertise());
        trainer.setExperience(trainerDetails.getExperience());
        trainer.setMinBudget(trainerDetails.getMinBudget());
        trainer.setTrainingId(trainerDetails.getTrainingId());

        return trainerRepository.save(trainer);
    }

    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    public List<TrainerInfo> getTrainersByTrainingId(Long trainingId) {
        return trainerRepository.findByTrainingId(trainingId);
    }

    public List<TrainerInfo> getAvailableTrainersByExpertise(List<String> expertiseList) {
        return trainerRepository.findByStatusAndExpertise(TrainerInfo.Status.AVAILABLE, expertiseList);
    }

    public void updateTrainerStatus(Long id, TrainerInfo.Status status) {
        trainerRepository.findById(id).ifPresent(trainer -> {
            trainer.setStatus(status);
            trainerRepository.save(trainer);
        });
    }

    public List<TrainerInfo> findTrainersForTraining(List<String> requiredSkills, Double trainingBudget) {
        double maxTrainerBudget = trainingBudget * 0.8;
        List<String> requiredSkillsLower = requiredSkills.stream().map(String::toLowerCase).toList();

        return trainerRepository.findAll().stream()
                .filter(trainer -> trainer.getMinBudget() <= maxTrainerBudget)
                .filter(trainer -> trainer.getStatus() == TrainerInfo.Status.AVAILABLE)
                .filter(trainer -> {
                    List<String> trainerSkills = trainer.getExpertise().stream().map(String::toLowerCase).toList();
                    return new HashSet<>(trainerSkills).containsAll(requiredSkillsLower);
                })
                .sorted(Comparator.comparingDouble(TrainerInfo::getRating).reversed())
                .collect(Collectors.toList());
    }

    public List<TrainerDto> getTrainersBySkills(List<String> skills) {
        return trainerRepository.findAll().stream()
                .filter(trainer -> !Collections.disjoint(trainer.getExpertise(), skills))
                .map(trainer -> new TrainerDto(trainer.getTrainerId(), trainer.getTrainerName(), trainer.getMinBudget(), trainer.getExpertise()))
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "trainerService", fallbackMethod = "fallbackGetTrainerDetails")
    public ResponseDto getTrainerWithTraining(Long trainerId) throws ChangeSetPersister.NotFoundException {
        TrainerInfo trainer = getTrainerById(trainerId);

        List<TrainingDto> trainingList = webClientBuilder.build()
                .get()
                .uri("http://localhost:9098/trainings/trainer/" + trainerId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TrainingDto>>() {})
                .block();

        ResponseDto responseDto = new ResponseDto();
        responseDto.setTrainer(mapToTrainerDto(trainer));
        responseDto.setTrainingList(trainingList);

        return responseDto;
    }

    @CircuitBreaker(name = "trainerService", fallbackMethod = "fallbackGetTrainingDetails")
    public TrainingDto getTraining(Long trainerId) {
        List<TrainingDto> trainingList = webClientBuilder.build()
                .get()
                .uri("http://localhost:9098/trainings/trainer/" + trainerId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TrainingDto>>() {})
                .block();

        return trainingList != null && !trainingList.isEmpty() ? trainingList.get(0) : new TrainingDto();
    }

    public void updateTrainerTraining(Long id, TrainingIDDto trainingIdDto) {
        trainerRepository.findById(id).ifPresent(trainer -> {
            trainer.setTrainingId(trainingIdDto.getTrainingId());
            trainerRepository.save(trainer);
        });
    }

    public ResponseDto fallbackGetTrainerDetails(Long trainerId, Throwable throwable) {
        return new ResponseDto();
    }

    public TrainingDto fallbackGetTrainingDetails(Long trainerId, Throwable throwable) {
        return new TrainingDto();
    }

    private TrainerDto mapToTrainerDto(TrainerInfo trainer) {
        TrainerDto dto = new TrainerDto();
        dto.setTrainerId(trainer.getTrainerId());
        dto.setTrainerName(trainer.getTrainerName());
        dto.setEmail(trainer.getEmail());
        dto.setPhone(trainer.getPhone());
        dto.setExpertise(trainer.getExpertise());
        dto.setExperience(trainer.getExperience());
        dto.setMinBudget(trainer.getMinBudget());
        dto.setTrainingId(trainer.getTrainingId());
        return dto;
    }
}


package com.example.TrainingInfo.service;


import com.example.TrainingInfo.dto.TrainerDTO;
import com.example.TrainingInfo.dto.TrainerMatchRequest;
import com.example.TrainingInfo.model.TrainingIn;
import com.example.TrainingInfo.repository.TrainingInRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainingInService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingInService.class);

    @Autowired
    private TrainingInRepository TrainingInRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${trainer.service.url}")
    private String trainerServiceUrl;

    // Fetch all TrainingIns
    public List<TrainingIn> getAllTrainings() {
        return TrainingInRepository.findAll();
    }

    // Fetch TrainingIn by ID
    public TrainingIn getTrainingById(Long id) throws Exception {
        return TrainingInRepository.findById(id)
                .orElseThrow(() -> new Exception("Training with ID " + id + " not found"));
    }

    public TrainingIn updateTraining(Long id, TrainingIn TrainingInDetails) throws Exception {
        TrainingIn TrainingIn = getTrainingById(id);

        TrainingIn.setTrainingName(TrainingInDetails.getTrainingName());
        TrainingIn.setSkills(TrainingInDetails.getSkills());
        TrainingIn.setDuration(TrainingInDetails.getDuration());
        TrainingIn.setBudget(TrainingInDetails.getBudget());

        return TrainingInRepository.save(TrainingIn);
    }

    // Delete TrainingIn
    public void deleteTraining(Long id) throws Exception {
        TrainingIn TrainingIn = getTrainingById(id);
        TrainingInRepository.delete(TrainingIn);
    }

    // Assign a specific trainer manually
    public String assignTrainer(Long TrainingInId, Long trainerId) throws Exception {
        int assignedTrainingIns = TrainingInRepository.countByTrainerId(trainerId);
        if (assignedTrainingIns >= 2) {
            throw new Exception("Trainer already assigned to 2 TrainingIns");
        }

        TrainingIn TrainingIn = getTrainingById(TrainingInId);
        TrainingIn.setTrainerId(trainerId);
        TrainingInRepository.save(TrainingIn);

        return "Trainer assigned successfully";
    }

    // Create a TrainingIn and auto-assign the best trainer
    @Transactional
    public TrainingIn createTraining(TrainingIn TrainingIn) {
        logger.info("Creating new TrainingIn: {}", TrainingIn);

        TrainerMatchRequest request = new TrainerMatchRequest(TrainingIn.getSkills(), TrainingIn.getBudget());
        List<TrainerDTO> availableTrainers = fetchAvailableTrainers(request);

        if (availableTrainers.isEmpty()) {
            logger.warn("No eligible trainers found. Creating TrainingIn without trainer.");
            return TrainingInRepository.save(TrainingIn);
        }

        // Count how many TrainingIns each trainer is assigned to
        Map<Long, Integer> trainerAssignmentCounts = countTrainerAssignmentsBatch(
                availableTrainers.stream().map(TrainerDTO::getTrainerId).collect(Collectors.toList())
        );

        // Pick the highest-rated trainer who has less than 2 assignments
        TrainerDTO bestTrainer = availableTrainers.stream()
                .filter(t -> trainerAssignmentCounts.getOrDefault(t.getTrainerId(), 0) < 2)
                .max(Comparator.comparingDouble(TrainerDTO::getRating))
                .orElse(null);

        if (bestTrainer != null) {
            TrainingIn.setTrainerId(bestTrainer.getTrainerId());
            TrainingIn savedTrainingIn = TrainingInRepository.save(TrainingIn);

            updateTrainerStatus(bestTrainer.getTrainerId());
            updateTrainerWithTrainingInId(bestTrainer.getTrainerId(), savedTrainingIn.getTrainingId());

            logger.info("Assigned trainer: {} to TrainingIn ID {}", bestTrainer.getTrainerName(), savedTrainingIn.getTrainingId());
            return savedTrainingIn;
        } else {
            logger.warn("No suitable trainer available. Saving TrainingIn without trainer.");
            return TrainingInRepository.save(TrainingIn);
        }
    }

    // Fetch eligible trainers from trainer microservice
    private List<TrainerDTO> fetchAvailableTrainers(TrainerMatchRequest request) {
        return webClientBuilder.build()
                .post()
                .uri(trainerServiceUrl + "/trainers/eligible")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(TrainerDTO.class)
                .collectList()
                .block();
    }

    // Count how many TrainingIns each trainer is currently assigned to
    private Map<Long, Integer> countTrainerAssignmentsBatch(List<Long> trainerIds) {
        Map<Long, Integer> assignments = new HashMap<>();
        for (Long trainerId : trainerIds) {
            assignments.put(trainerId, TrainingInRepository.countByTrainerId(trainerId));
        }
        return assignments;
    }

    // Update trainer's availability status
    private void updateTrainerStatus(Long trainerId) {
        webClientBuilder.build()
                .put()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(9097)
                        .path("/api/trainers/{id}/status")
                        .queryParam("status", "NOT_AVAILABLE")
                        .build(trainerId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    // Update trainer with assigned TrainingIn ID
    private void updateTrainerWithTrainingInId(Long trainerId, Long TrainingInId) {
        webClientBuilder.build()
                .put()
                .uri("http://localhost:9097/api/trainers/TrainingIns/" + trainerId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("TrainingInId", TrainingInId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    // Get TrainingIns by trainer ID
    public List<TrainingIn> getTrainingByTrainerId(Long trainerId) {
        return TrainingInRepository.findByTrainerId(trainerId);
    }

    // Get TrainingIns by vendor ID
    public List<TrainingIn> getByVendorID(Long vendorId) {
        return TrainingInRepository.findByVendorId(vendorId);
    }
}


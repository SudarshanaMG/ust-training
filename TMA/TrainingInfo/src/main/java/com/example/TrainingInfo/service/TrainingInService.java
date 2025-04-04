package com.example.TrainingInfo.service;

import com.example.TrainingInfo.dto.Responsedto;
import com.example.TrainingInfo.dto.Trainingdto;
import com.example.TrainingInfo.model.TrainingIn;
import com.example.TrainingInfo.repository.TrainingInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingInService {
    @Autowired
    private final TrainingInRepository trainingRepository;
    @Autowired
    private final WebClient webClient;

    public TrainingInService(TrainingInRepository trainingRepository, WebClient.Builder webClientBuilder) {
        this.trainingRepository = trainingRepository;
        this.webClient = webClientBuilder.baseUrl("http://TRAINERINFO-SERVICE").build(); // Eureka Service Name
    }

    public Trainingdto addTraining(TrainingIn request) {
        TrainingIn training = new TrainingIn();

        training.setName(request.getName());
        training.setOrganization(request.getOrganization());
        training.setTrainerId(request.getId());
        training.setStartDate(LocalDate.parse(request.getStartDate().toString()));
        training.setEndDate(LocalDate.parse(request.getEndDate().toString()));
        training.setSkills(request.getSkills());

        training.setDuration(TrainingIn.DurationType.valueOf(request.getDuration().toString().toUpperCase())); // Ensure enum conversion
        training.setNoOfBatches(request.getNoOfBatches());
        training.setNoOfStudentsPerBatch(request.getNoOfStudentsPerBatch());
        training.setBudget(request.getBudget());
        training.setEmail(request.getEmail());
        training.setPhone(request.getPhone());
        training.setToc(request.getToc());
        training.setPoc(request.getPoc());
        training.setStatus(TrainingIn.TrainingStatus.valueOf(request.getStatus().toString().toUpperCase())); // Ensure enum conversion

        TrainingIn savedTraining = trainingRepository.save(training);
        return mapToDTO(savedTraining);
    }

    public List<Trainingdto> getAllTrainings() {
        return trainingRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<Trainingdto> getTrainingById(Long id) {
        return trainingRepository.findById(id).map(this::mapToDTO);
    }

    public List<String> getTrainingSkillsById(Long id) {
        return trainingRepository.findById(id)
                .map(training -> mapToDTO(training).getSkills())
                .orElse(Collections.emptyList());
    }



    public Trainingdto updateTraining(Long id, Responsedto request) {
        return trainingRepository.findById(id)
                .map(training -> {
                    training.setName(request.getName());
                    training.setOrganization(request.getOrgName());
                    training.setTrainerId(request.getId());
                    training.setStartDate(LocalDate.parse(request.getStartDate()));
                    training.setEndDate(LocalDate.parse(request.getEndDate()));
                    training.setSkills(request.getSkills());
                    TrainingIn updatedTraining = trainingRepository.save(training);
                    return mapToDTO(updatedTraining);
                }).orElse(null);
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }

    private Trainingdto mapToDTO(TrainingIn training) {
        Responsedto trainerInfo = webClient.get()
                .uri("/trainers/" + training.getTrainerId()) // Calling TrainerInfo Service
                .retrieve()
                .bodyToMono(Responsedto.class)
                .block(); // Blocking call for simplicity (Consider using reactive approach in large applications)

        return new Trainingdto(
                training.getId(),
                training.getName(),
                training.getOrganization(),
                training.getStartDate(),
                training.getEndDate(),
                training.getSkills()
        );
    }
}


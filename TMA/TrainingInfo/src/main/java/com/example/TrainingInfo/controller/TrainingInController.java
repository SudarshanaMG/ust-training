package com.example.TrainingInfo.controller;

import com.example.TrainerInfo_service.model.TrainerInfo;
import com.example.TrainingInfo.dto.Responsedto;
import com.example.TrainingInfo.dto.Trainingdto;
import com.example.TrainingInfo.model.TrainingIn;
import com.example.TrainingInfo.service.TrainerSearchService;
import com.example.TrainingInfo.service.TrainingInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingInController {
    private final TrainingInService trainingService;
    private final TrainerSearchService trainerSearchService;

    public TrainingInController(TrainingInService trainingService, TrainerSearchService trainerSearchService) {
        this.trainingService = trainingService;
        this.trainerSearchService = trainerSearchService;
    }

    @PostMapping
    public ResponseEntity<Trainingdto> addTraining(@RequestBody TrainingIn request) {
        return ResponseEntity.ok(trainingService.addTraining(request));
    }

    @GetMapping
    public ResponseEntity<List<Trainingdto>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.getAllTrainings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainingdto> getTrainingById(@PathVariable Long id) {
        return trainingService.getTrainingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/find-trainers")
    public ResponseEntity<List<TrainerInfo>> findTrainersForTraining(@RequestParam int budget, Long trainingId){
        List<String> trainingSkills = getTrainingSkills(trainingId);
        List<TrainerInfo> trainers = trainerSearchService.getMatchingTrainers(budget, trainingSkills);
        return ResponseEntity.ok(trainers);
    }

    private List<String> getTrainingSkills(Long trainingId){
        return trainingService.getTrainingSkillsById(trainingId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainingdto> updateTraining(@PathVariable Long id, @RequestBody Responsedto request) {
        Trainingdto updated = trainingService.updateTraining(id, request);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }
}


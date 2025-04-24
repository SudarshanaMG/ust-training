package com.example.TrainerInfo_service.Controller;
import com.example.TrainerInfo_service.Service.TrainerInfoService;
import com.example.TrainerInfo_service.dto.*;
import com.example.TrainerInfo_service.model.TrainerInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerInfoController {

    @Autowired
    private TrainerInfoService trainerService;

    @PostMapping
    public ResponseEntity<TrainerInfo> createTrainer(@Valid @RequestBody TrainerInfo trainer) {
        return ResponseEntity.ok(trainerService.createTrainer(trainer));
    }

    @GetMapping
    public ResponseEntity<List<TrainerInfo>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerInfo> getTrainerById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerInfo> updateTrainer(@PathVariable Long id, @Valid @RequestBody TrainerInfo trainerDetails) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainerDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<TrainerInfo>> getAvailableTrainers(@RequestParam List<String> expertise) {
        return ResponseEntity.ok(trainerService.getAvailableTrainersByExpertise(expertise));
    }

    @PostMapping("/eligible")
    public ResponseEntity<List<TrainerInfo>> getEligibleTrainers(@RequestBody TrainerMatchRequest request) {
        return ResponseEntity.ok(trainerService.findTrainersForTraining(request.getSkills(), request.getBudget()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateTrainerStatus(@PathVariable Long id, @RequestParam TrainerInfo.Status status) {
        trainerService.updateTrainerStatus(id, status);
        return ResponseEntity.ok("Trainer status updated");
    }

    @GetMapping("/training/{trainingId}")
    public ResponseEntity<List<TrainerInfo>> getTrainersByTrainingId(@PathVariable Long trainingId) {
        return ResponseEntity.ok(trainerService.getTrainersByTrainingId(trainingId));
    }

    @GetMapping("/bySkills")
    public ResponseEntity<List<TrainerDto>> getTrainersBySkills(@RequestParam List<String> skills) {
        return ResponseEntity.ok(trainerService.getTrainersBySkills(skills));
    }

    @GetMapping("/withTraining/{trainerId}")
    public ResponseEntity<ResponseDto> getTrainerWithTraining(@PathVariable Long trainerId) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(trainerService.getTrainerWithTraining(trainerId));
    }

    @GetMapping("/trainings/{trainerId}")
    public ResponseEntity<TrainingDto> getTraining(@PathVariable Long trainerId) {
        return ResponseEntity.ok(trainerService.getTraining(trainerId));
    }

    @PutMapping("/trainings/{trainerId}")
    public ResponseEntity<String> updateTraining(@PathVariable Long trainerId, @RequestBody TrainingIDDto trainingIdDto) {
        trainerService.updateTrainerTraining(trainerId, trainingIdDto);
        return ResponseEntity.ok("Trainer training updated");
    }
}

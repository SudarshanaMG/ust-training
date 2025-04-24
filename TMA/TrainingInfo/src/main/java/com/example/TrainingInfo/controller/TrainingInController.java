package com.example.TrainingInfo.controller;

import com.example.TrainingInfo.model.TrainingIn;
import com.example.TrainingInfo.service.TrainingInService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingInController {
    @Autowired
    private TrainingInService trainingService;

    // Create training (auto-assigns trainer if available)
    @PostMapping
    public TrainingIn createTraining(@Valid @RequestBody TrainingIn training) {
        return trainingService.createTraining(training);
    }

    // Get all trainings
    @GetMapping
    public List<TrainingIn> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    // Get training by ID
    @GetMapping("/{id}")
    public TrainingIn getTrainingById(@PathVariable Long id) throws Exception {
        return trainingService.getTrainingById(id);
    }

    // Get trainings by trainer ID
    @GetMapping("/trainer/{id}")
    public List<TrainingIn> getTrainingByTrainerId(@PathVariable Long id) {
        return trainingService.getTrainingByTrainerId(id);
    }

    // Get trainings by vendor ID
    @GetMapping("/vendor/{id}")
    public List<TrainingIn> getByVendorId(@PathVariable Long id) {
        return trainingService.getByVendorID(id);
    }

    // Update training
    @PutMapping("/{id}")
    public TrainingIn updateTraining(@PathVariable Long id, @Valid @RequestBody TrainingIn trainingDetails) throws Exception {
        return trainingService.updateTraining(id, trainingDetails);
    }

    // Delete training
    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable Long id) throws Exception {
        trainingService.deleteTraining(id);
    }

    // Assign a trainer manually
    @PostMapping("/{trainingId}/assign-trainer/{trainerId}")
    public String assignTrainer(@PathVariable Long trainingId, @PathVariable Long trainerId) throws Exception {
        return trainingService.assignTrainer(trainingId, trainerId);
    }
}


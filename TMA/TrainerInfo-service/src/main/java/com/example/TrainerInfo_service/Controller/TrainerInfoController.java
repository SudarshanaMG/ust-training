package com.example.TrainerInfo_service.Controller;
import com.example.TrainerInfo_service.Service.TrainerInfoService;
import com.example.TrainerInfo_service.model.TrainerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerInfoController {
    @Autowired
    private final TrainerInfoService trainerInfoService;

    public TrainerInfoController(TrainerInfoService trainerInfoService) {
        this.trainerInfoService = trainerInfoService;
    }

    @PostMapping
    public ResponseEntity<TrainerInfo> addTrainer(@RequestBody TrainerInfo trainerInfo) {
        return ResponseEntity.ok(trainerInfoService.addTrainer(trainerInfo));
    }

    @GetMapping
    public ResponseEntity<List<TrainerInfo>> getAllTrainers() {
        return ResponseEntity.ok(trainerInfoService.getAllTrainers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerInfo> getTrainerById(@PathVariable Long id) {
        return trainerInfoService.getTrainerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/skill/{skill}")
    public ResponseEntity<List<TrainerInfo>> getTrainersBySkill(@PathVariable String skills) {
        return ResponseEntity.ok(trainerInfoService.getTrainersBySkill(skills));
    }

    @GetMapping("/budget/{budget}")
    public ResponseEntity<List<TrainerInfo>> getTrainersByBudget(@PathVariable int budget) {
        return ResponseEntity.ok(trainerInfoService.getTrainersByBudget(budget));
    }

    @GetMapping("/search/")
    public ResponseEntity<List<TrainerInfo>> searchTrainers(@RequestParam int budget, @RequestParam List<String> skills) {
        List<TrainerInfo> list = trainerInfoService.findMatchingTrainers(budget, skills, TrainerInfo.Status.valueOf("AVAILABLE"));
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        trainerInfoService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }
}

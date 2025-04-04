package com.example.TrainingInfo.service;

import com.example.TrainerInfo_service.model.TrainerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
@Service
public class TrainerSearchService {

    @Autowired
    private final WebClient webClient;

    public TrainerSearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://TRAINERINFO-SERVICE").build(); // Eureka Service Name
    }
    public List<TrainerInfo> getMatchingTrainers(int budget, List<String> skills) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path("/trainers/search").queryParam("budget", budget).queryParam("skills", String.join(",", skills)).build()).retrieve().bodyToFlux(TrainerInfo.class).collectList().block();
    }
}

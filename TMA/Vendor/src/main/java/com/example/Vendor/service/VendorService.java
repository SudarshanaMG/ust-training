package com.example.Vendor.service;

import com.example.Vendor.dto.*;
import com.example.Vendor.model.Vendor;
import com.example.Vendor.repository.VendorRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9097").build();
    }

    @CircuitBreaker(name = "vendorService", fallbackMethod = "fallbackGetVendorWithTrainingsAndTrainers")
    public VendorResponseDTO getVendorWithTrainingsAndTrainers(String vendorId) {
        // Fetch Vendor
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found with ID " + vendorId));
        VendorDTO vendorDTO = mapToVendorDTO(vendor);

        // Fetch Trainings by Vendor
        List<TrainingDTO> trainingList = webClient.get()
                .uri("http://localhost:9098/api/trainings/vendor/" + vendorId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TrainingDTO>>() {})
                .block();

        // Fetch Trainers for Each Training
        List<TrainingWithTrainersDTO> trainingWithTrainersList = trainingList.stream().map(training -> {
            List<TrainerDTO> trainerList = webClient.get()
                    .uri("http://localhost:9097/api/trainers/training/" + training.getTrainingId())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<TrainerDTO>>() {})
                    .block();
            return new TrainingWithTrainersDTO(training, trainerList);
        }).collect(Collectors.toList());

        // Construct Response DTO
        return new VendorResponseDTO(vendorDTO, trainingWithTrainersList);
    }

    public VendorResponseDTO fallbackGetVendorWithTrainingsAndTrainers(String vendorId, Throwable throwable) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found with ID " + vendorId));

        VendorDTO vendorDTO = mapToVendorDTO(vendor);
        return new VendorResponseDTO(vendorDTO, List.of()); // Return empty training list
    }

    private VendorDTO mapToVendorDTO(Vendor vendor) {
        return new VendorDTO(vendor.getName(), vendor.getEmail(), vendor.getPhone(), vendor.getWebsite(),
                vendor.getAddress(), vendor.getGSTIN(), vendor.getHeadOfficeLocation());
    }


    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(String id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendor not found with ID " + id));
    }

    public Vendor updateVendor(String id, Vendor vendorDetails) {
        return vendorRepository.findById(id).map(vendor -> {
            vendor.setName(vendorDetails.getName());
            vendor.setEmail(vendorDetails.getEmail());
            vendor.setPhone(vendorDetails.getPhone());
            vendor.setWebsite(vendorDetails.getWebsite());
            vendor.setAddress(vendorDetails.getAddress());
            return vendorRepository.save(vendor);
        }).orElseThrow(() -> new NotFoundException("Vendor not found"));
    }

    public void deleteVendor(String id) {
        if (!vendorRepository.existsById(id)) {
            throw new NotFoundException("Vendor not found with ID " + id);
        }
        vendorRepository.deleteById(id);
    }


    public List<Vendor> getVendorsByHeadOfficeLocation(String location) {
        List<Vendor> vendors = vendorRepository.findByHeadOfficeLocation(location);
        if (vendors.isEmpty()) {
            throw new NotFoundException("No vendors found at location: " + location);
        }
        return vendors;
    }
}



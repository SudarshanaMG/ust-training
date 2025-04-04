package com.example.Vendor.service;

import com.example.Vendor.dto.Responsedto;
import com.example.Vendor.model.Vendor;
import com.example.Vendor.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final WebClient webClient;

    public VendorService(VendorRepository vendorRepository, WebClient.Builder webClientBuilder) {
        this.vendorRepository = vendorRepository;
        this.webClient = webClientBuilder.baseUrl("http://EXTERNAL-SERVICE").build(); // Replace with actual service name
    }

    public Vendor addVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<Responsedto> getVendorById(Long id) {
        return vendorRepository.findById(id).map(this::mapToDTO);
    }

    public Vendor updateVendor(Long id, Vendor updatedVendor) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    vendor.setName(updatedVendor.getName());
                    vendor.setEmail(updatedVendor.getEmail());
                    vendor.setPhone(updatedVendor.getPhone());
                    vendor.setAddress(updatedVendor.getAddress());
                    return vendorRepository.save(vendor);
                }).orElse(null);
    }

    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    private Responsedto mapToDTO(Vendor vendor) {
        String additionalInfo = webClient.get()
                .uri("/external-data/" + vendor.getId()) // Replace with actual external API endpoint
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Blocking call for simplicity

        return new Responsedto(
                vendor.getId(),
                vendor.getName(),
                vendor.getEmail(),
                vendor.getPhone(),
                vendor.getAddress(),
                additionalInfo
        );
    }
}



package com.example.Vendor.controller;

import com.example.Vendor.dto.VendorResponseDTO;
import com.example.Vendor.model.Vendor;
import com.example.Vendor.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @PostMapping
    public Vendor createVendor(@Valid @RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("{id}")
    public Vendor getVendorById(@PathVariable String id) throws Exception {
        return vendorService.getVendorById(id);
    }

    @GetMapping("/with-trainings/{vendorId}")
    public ResponseEntity<VendorResponseDTO> getVendorWithTrainings(@PathVariable String vendorId) {
        VendorResponseDTO response = vendorService.getVendorWithTrainingsAndTrainers(vendorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("location/{headOfficeLocation}")
    public List<Vendor> getVendorByHeadOfficeLocation(@PathVariable String headOfficeLocation) throws Exception {
        return vendorService.getVendorsByHeadOfficeLocation(headOfficeLocation);
    }

    @PutMapping("{id}")
    public Vendor updateVendor(@PathVariable String id, @Valid @RequestBody Vendor vendorDetails) {
        return vendorService.updateVendor(id, vendorDetails);
    }

    @DeleteMapping("{id}")
    public void deleteVendor(@PathVariable String id) {
        vendorService.deleteVendor(id);
    }
}


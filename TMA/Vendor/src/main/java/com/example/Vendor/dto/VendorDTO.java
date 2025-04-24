package com.example.Vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendorDTO {
    private String name;
    private String email;
    private String phone;
    private String website;
    private String address;
    private String GSTIN;
    private String headOfficeLocation;
}

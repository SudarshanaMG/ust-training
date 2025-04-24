package com.example.Vendor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "vendor")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    @NotBlank(message = "Vendor name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Invalid phone number")
    private String phone;

    private String website;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ContactPerson> contactPersons;

    @NotBlank(message = "Address is required")
    private String address;

    //    @Pattern(regexp = "\\[0-9A-Z]", message = "Invalid GSTIN")
    private String GSTIN;

    @NotBlank(message = "Head office location is required")
    private String headOfficeLocation;

}

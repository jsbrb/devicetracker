package com.example.devicetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String uniqueIdentifier;

    @OneToMany(mappedBy = "device")
    @JsonIgnoreProperties("device")
    private List<Movement> movements;
}

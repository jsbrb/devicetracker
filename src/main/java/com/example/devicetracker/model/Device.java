package com.example.devicetracker.model;

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
    private List<Movement> movements;
}

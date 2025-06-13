package com.example.devicetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("devices")
    private User user;
}

package com.example.devicetracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;

    @ManyToOne
    private User user;

    @ManyToOne
    private Device device;
}

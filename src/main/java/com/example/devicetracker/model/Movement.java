package com.example.devicetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties("movements") // evita loops de serialización
    private User user;

    @ManyToOne
    @JoinColumn(name = "device_id")
    @JsonIgnoreProperties("movements") // evita loops de serialización
    private Device device;
}

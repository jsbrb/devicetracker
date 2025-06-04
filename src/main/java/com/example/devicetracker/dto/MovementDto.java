package com.example.devicetracker.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovementDto {
    private Long id;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
    private Long userId;
    private Long deviceId;
}

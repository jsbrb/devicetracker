package com.example.devicetracker.mapper;

import com.example.devicetracker.dto.MovementDto;
import com.example.devicetracker.model.Device;
import com.example.devicetracker.model.Movement;
import com.example.devicetracker.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class MovementMapper {

    //Convierte de DTO a Entidad
    public static Movement toEntity(MovementDto dto, User user, Device device){
        Movement movement = new Movement();
        movement.setId(dto.getId());
        movement.setLatitude(dto.getLatitude());
        movement.setLongitude(dto.getLongitude());
        movement.setTimestamp(dto.getTimestamp());
        movement.setUser(user);
        movement.setDevice(device);
        return movement;
    }

    //Concierte de Entidad a DTO
    public static MovementDto toDto(Movement movement){
        MovementDto dto = new MovementDto();
        dto.setId(movement.getId());
        dto.setLatitude(movement.getLatitude());
        dto.setLongitude(movement.getLongitude());
        dto.setTimestamp(movement.getTimestamp());
        dto.setUserId(movement.getUser() != null ? movement.getUser().getId() : null);
        dto.setDeviceId(movement.getDevice() != null ? movement.getDevice().getId() : null);
        return dto;
    }

    // Convertir lista de Entidades a lista de DTOs
    public static List<MovementDto> toDtoList(List<Movement> movements) {
        return movements.stream()
                .map(MovementMapper::toDto)
                .collect(Collectors.toList());
    }
}

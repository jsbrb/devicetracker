package com.example.devicetracker.controller;

import com.example.devicetracker.dto.DeviceDto;
import com.example.devicetracker.dto.MovementDto;
import com.example.devicetracker.mapper.DeviceMapper;
import com.example.devicetracker.mapper.MovementMapper;
import com.example.devicetracker.model.Device;
import com.example.devicetracker.model.Movement;
import com.example.devicetracker.repository.DeviceRepository;
import com.example.devicetracker.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private MovementRepository movementRepository;

    //Crear un dispositivo
    @PostMapping
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto deviceDto) {
        Device device = DeviceMapper.toEntity(deviceDto);
        Device savedDevice = deviceRepository.save(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(DeviceMapper.toDto(savedDevice)); // 201 Created
    }

    // Obtener la última localización
    @GetMapping("/{id}/last-location")
    public ResponseEntity<MovementDto> lastLocation(@PathVariable Long id) {
        return movementRepository.findByDeviceIdOrderByTimestampDesc(id).stream()
                .findFirst()
                .map(movement -> ResponseEntity.ok(MovementMapper.toDto(movement))) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // Obtener historial de movimientos
    @GetMapping("/{id}/history")
    public ResponseEntity<List<MovementDto>> history(@PathVariable Long id) {
        List<Movement> movements = movementRepository.findByDeviceIdOrderByTimestampDesc(id);
        if (movements.isEmpty()) {
            return ResponseEntity.notFound().build(); // O puedes devolver ResponseEntity.ok(Collections.emptyList())
        }
        List<MovementDto> dtoList = movements.stream()
                .map(MovementMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

}

package com.example.devicetracker.controller;

import com.example.devicetracker.dto.MovementDto;
import com.example.devicetracker.mapper.MovementMapper;
import com.example.devicetracker.model.Device;
import com.example.devicetracker.model.Movement;
import com.example.devicetracker.model.User;
import com.example.devicetracker.repository.DeviceRepository;
import com.example.devicetracker.repository.MovementRepository;
import com.example.devicetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/movements")
public class MovementController {
    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    //Crear movimiento
    @PostMapping
    public ResponseEntity<MovementDto> create(@RequestBody MovementDto dto) {
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        Optional<Device> deviceOptional = deviceRepository.findById(dto.getDeviceId());

        if (userOptional.isEmpty() || deviceOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();    // 400 si user o device no existen
        }
        Movement movement = MovementMapper.toEntity(dto, userOptional.get(), deviceOptional.get());
        Movement saved = movementRepository.save(movement);

        return ResponseEntity.status(201).body(MovementMapper.toDto(saved));

    }

    //Devolver movimiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<MovementDto> getByID(@PathVariable Long id){
        return movementRepository.findById(id)
                .map(m-> ResponseEntity.ok(MovementMapper.toDto(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    //Update Movement
    @PutMapping("/{id}")
    public ResponseEntity<MovementDto> update(@PathVariable Long id, @RequestBody MovementDto dto) {
        Optional<Movement> optMovement = movementRepository.findById(id);
        if (optMovement.isEmpty()) return ResponseEntity.notFound().build();

        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Device> deviceOpt = deviceRepository.findById(dto.getDeviceId());
        if (userOpt.isEmpty() || deviceOpt.isEmpty()) return ResponseEntity.badRequest().build();

        Movement movement = optMovement.get();
        movement.setLatitude(dto.getLatitude());
        movement.setLongitude(dto.getLongitude());
        movement.setTimestamp(dto.getTimestamp());
        movement.setUser(userOpt.get());
        movement.setDevice(deviceOpt.get());

        Movement saved = movementRepository.save(movement);
        return ResponseEntity.ok(MovementMapper.toDto(saved));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!movementRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movementRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

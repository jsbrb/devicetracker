package com.example.devicetracker.controller;

import com.example.devicetracker.model.Movement;
import com.example.devicetracker.repository.DeviceRepository;
import com.example.devicetracker.repository.MovementRepository;
import com.example.devicetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    //Create
    @PostMapping
    public Movement createMovement(@RequestBody Movement movement){
        return movementRepository.save(movement);
    }

    //Get all movements
    @GetMapping
    public List<Movement> getAllMovements(){
        return movementRepository.findAll();
    }

    //Get a movement by ID
    @GetMapping("/{id}")
    public Movement getMovementById(@PathVariable Long id){
        return movementRepository.findById(id).orElse(null);
    }

    //Update Movement
    @PutMapping("/{id}")
    public Movement updateMovement(@PathVariable Long id, @RequestBody Movement updatedMovement) {
        Optional<Movement> optionalMovement = movementRepository.findById(id);
        if (optionalMovement.isPresent()) {
            Movement movement = optionalMovement.get();
            movement.setLatitude(updatedMovement.getLatitude());
            movement.setLongitude(updatedMovement.getLongitude());
            movement.setTimestamp(updatedMovement.getTimestamp());
            movement.setUser(updatedMovement.getUser());
            movement.setDevice(updatedMovement.getDevice());
            return movementRepository.save(movement);
        }
        return null;
    }

    //Delete Movement
    @DeleteMapping("/{id}")
    public void deleteMovement(@PathVariable Long id){
        movementRepository.deleteById(id);
    }
}

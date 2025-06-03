package com.example.devicetracker.controller;

import com.example.devicetracker.model.Device;
import com.example.devicetracker.model.Movement;
import com.example.devicetracker.repository.DeviceRepository;
import com.example.devicetracker.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private MovementRepository movementRepository;

    //Create
    @PostMapping
    public Device create(@RequestBody Device device){
        return deviceRepository.save(device);
    }

    //GetAll
    @GetMapping("/{id}/last-location")
    public Movement lastLocation(@PathVariable Long id){
        return movementRepository.findByDeviceIdOrderByTimestampDesc(id).stream().findFirst().orElseThrow(null);
    }

    //Get Historico
    @GetMapping("/{id}/history")
    public List<Movement> history(@PathVariable Long id){
        return movementRepository.findByDeviceIdOrderByTimestampDesc(id);
    }

}

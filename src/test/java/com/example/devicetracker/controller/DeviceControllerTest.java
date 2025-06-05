package com.example.devicetracker.controller;

import com.example.devicetracker.dto.DeviceDto;
import com.example.devicetracker.mapper.DeviceMapper;
import com.example.devicetracker.model.Device;
import com.example.devicetracker.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceControllerTest {
    //Simulamos el repositorio
    @Mock
    private DeviceRepository deviceRepository;

    //Instancia real del controlador, con repo "mockeado"
    @InjectMocks
    private DeviceController deviceController;

    private Device device;
    private DeviceDto deviceDto;

    @BeforeEach
    void setup(){
        device = new Device();
        device.setId(1L);
        device.setName("Sensor A");
        device.setUniqueIdentifier("ABC-123");

        deviceDto = DeviceMapper.toDto(device);
    }

    @Test
    void testCreatedDevice(){
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        ResponseEntity<DeviceDto> response = deviceController.create(deviceDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Sensor A", response.getBody().getName());

        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void testGetDeviceByIdFound() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        ResponseEntity<DeviceDto> response = deviceController.getDevice(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sensor A", response.getBody().getName());
    }

    @Test
    void testGetDeviceByIdNotFound() {
        when(deviceRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<DeviceDto> response = deviceController.getDevice(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

}
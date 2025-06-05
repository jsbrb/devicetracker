package com.example.devicetracker.controller;

import com.example.devicetracker.dto.MovementDto;
import com.example.devicetracker.mapper.MovementMapper;
import com.example.devicetracker.model.Device;
import com.example.devicetracker.model.Movement;
import com.example.devicetracker.model.User;
import com.example.devicetracker.repository.DeviceRepository;
import com.example.devicetracker.repository.MovementRepository;
import com.example.devicetracker.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MovementController.class)
public class MovementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Para convertir objetos a JSON
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovementRepository movementRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DeviceRepository deviceRepository;

    @InjectMocks
    private MovementController movementController;

    private User user;
    private Device device;
    private Movement movement;
    private MovementDto movementDto;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);

        device = new Device();
        device.setId(1L);

        movement = new Movement();
        movement.setId(1L);
        movement.setLatitude(40.0);
        movement.setLongitude(-3.0);
        movement.setTimestamp(LocalDateTime.from(Instant.now()));
        movement.setUser(user);
        movement.setDevice(device);

        movementDto = MovementMapper.toDto(movement);
    }

    @Test
    public void createMovement_ReturnsCreated() throws Exception {
        MovementDto dto = new MovementDto();
        dto.setUserId(user.getId());
        dto.setDeviceId(device.getId());
        dto.setLatitude(40.0);
        dto.setLongitude(-3.0);
        dto.setTimestamp(LocalDateTime.from(Instant.now()));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        when(movementRepository.save(any(Movement.class))).thenReturn(movement);

        mockMvc.perform(post("/api/movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.deviceId").value(device.getId()))
                .andExpect(jsonPath("$.latitude").value(40.0))
                .andExpect(jsonPath("$.longitude").value(-3.0));
    }

    @Test
    public void getMovementById_ReturnsOk() throws Exception {
        when(movementRepository.findById(1L)).thenReturn(Optional.of(movement));

        mockMvc.perform(get("/api/movements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.deviceId").value(device.getId()));
    }

    @Test
    public void getMovementById_ReturnsNotFound() throws Exception {
        when(movementRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/movements/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMovement_success() {
        // Simulamos que el movimiento existe
        when(movementRepository.findById(movement.getId())).thenReturn(Optional.of(movement));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        when(movementRepository.save(any(Movement.class))).thenAnswer(i -> i.getArgument(0));

        ResponseEntity<MovementDto> response = movementController.update(movement.getId(), movementDto);

        assertEquals(200, response.getStatusCodeValue());
        MovementDto updatedDto = response.getBody();
        assertNotNull(updatedDto);
        assertEquals(movementDto.getLatitude(), updatedDto.getLatitude());
        assertEquals(movementDto.getLongitude(), updatedDto.getLongitude());

        verify(movementRepository).save(any(Movement.class));
    }

    @Test
    void updateMovement_notFound() {
        when(movementRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<MovementDto> response = movementController.update(999L, movementDto);

        assertEquals(404, response.getStatusCodeValue());
        verify(movementRepository, never()).save(any());
    }

    @Test
    void updateMovement_badRequest_whenUserOrDeviceNotFound() {
        when(movementRepository.findById(movement.getId())).thenReturn(Optional.of(movement));
        when(userRepository.findById(movementDto.getUserId())).thenReturn(Optional.empty());  // usuario no existe
        when(deviceRepository.findById(movementDto.getDeviceId())).thenReturn(Optional.of(device));

        ResponseEntity<MovementDto> response = movementController.update(movement.getId(), movementDto);

        assertEquals(400, response.getStatusCodeValue());
        verify(movementRepository, never()).save(any());

        // También probar si no existe device
        when(userRepository.findById(movementDto.getUserId())).thenReturn(Optional.of(user));
        when(deviceRepository.findById(movementDto.getDeviceId())).thenReturn(Optional.empty());

        response = movementController.update(movement.getId(), movementDto);

        assertEquals(400, response.getStatusCodeValue());
        verify(movementRepository, never()).save(any());
    }

    @Test
    void deleteMovement_success() {
        when(movementRepository.existsById(movement.getId())).thenReturn(true);
        doNothing().when(movementRepository).deleteById(movement.getId());

        ResponseEntity<Void> response = movementController.delete(movement.getId());

        assertEquals(204, response.getStatusCodeValue());
        verify(movementRepository).deleteById(movement.getId());
    }

    @Test
    void deleteMovement_notFound() {
        when(movementRepository.existsById(anyLong())).thenReturn(false);

        ResponseEntity<Void> response = movementController.delete(999L);

        assertEquals(404, response.getStatusCodeValue());
        verify(movementRepository, never()).deleteById(anyLong());
    }
}


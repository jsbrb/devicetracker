package com.example.devicetracker.mapper;

import com.example.devicetracker.dto.MovementDto;
import com.example.devicetracker.model.Device;
import com.example.devicetracker.model.Movement;
import com.example.devicetracker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovementMapperTest {

    private User user;
    private Device device;
    private MovementDto movementDto;
    private Movement movement;

    @BeforeEach
    public void setUp() {
        // Preparar los objetos necesarios para las pruebas
        user = new User();
        user.setId(1L);
        user.setName("User1");

        device = new Device();
        device.setId(1L);
        device.setName("Device1");
        device.setUniqueIdentifier("ABC123");

        movementDto = new MovementDto();
        movementDto.setId(1L);
        movementDto.setLatitude(12.34);
        movementDto.setLongitude(56.78);
        movementDto.setTimestamp(LocalDateTime.parse("2023-10-10T12:00:00"));
        movementDto.setUserId(user.getId());
        movementDto.setDeviceId(device.getId());

        movement = new Movement();
        movement.setId(1L);
        movement.setLatitude(12.34);
        movement.setLongitude(56.78);
        movement.setTimestamp(LocalDateTime.parse("2023-10-10T12:00:00"));
        movement.setUser(user);
        movement.setDevice(device);
    }

    // Test para toEntity()
    @Test
    public void testToEntity() {
        // Convertir el DTO a una entidad
        Movement entity = MovementMapper.toEntity(movementDto, user, device);

        // Verificar que la entidad tenga los mismos valores que el DTO
        assertEquals(movementDto.getId(), entity.getId());
        assertEquals(movementDto.getLatitude(), entity.getLatitude());
        assertEquals(movementDto.getLongitude(), entity.getLongitude());
        assertEquals(movementDto.getTimestamp(), entity.getTimestamp());
        assertEquals(user, entity.getUser());
        assertEquals(device, entity.getDevice());
    }

    // Test para toDto()
    @Test
    public void testToDto() {
        // Convertir la entidad a un DTO
        MovementDto dto = MovementMapper.toDto(movement);

        // Verificar que el DTO tenga los mismos valores que la entidad
        assertEquals(movement.getId(), dto.getId());
        assertEquals(movement.getLatitude(), dto.getLatitude());
        assertEquals(movement.getLongitude(), dto.getLongitude());
        assertEquals(movement.getTimestamp(), dto.getTimestamp());
        assertEquals(movement.getUser().getId(), dto.getUserId());
        assertEquals(movement.getDevice().getId(), dto.getDeviceId());
    }

    // Test para toDtoList()
    @Test
    public void testToDtoList() {
        // Crear una lista de movimientos
        List<Movement> movements = Arrays.asList(movement);

        // Convertir la lista de entidades a lista de DTOs
        List<MovementDto> dtos = MovementMapper.toDtoList(movements);

        // Verificar que la lista de DTOs tiene el tamaño correcto
        assertEquals(1, dtos.size());

        // Verificar que el primer DTO tiene los mismos valores que la entidad
        MovementDto dto = dtos.get(0);
        assertEquals(movement.getId(), dto.getId());
        assertEquals(movement.getLatitude(), dto.getLatitude());
        assertEquals(movement.getLongitude(), dto.getLongitude());
        assertEquals(movement.getTimestamp(), dto.getTimestamp());
        assertEquals(movement.getUser().getId(), dto.getUserId());
        assertEquals(movement.getDevice().getId(), dto.getDeviceId());
    }
}
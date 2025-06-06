package com.example.devicetracker.mapper;

import com.example.devicetracker.dto.DeviceDto;
import com.example.devicetracker.model.Device;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceMapperTest {
    // Test para el mapeo de DeviceDto a Device (toEntity)
    @Test
    public void testToEntity() {
        // Crear un DTO de prueba
        DeviceDto dto = new DeviceDto();
        dto.setId(1L);
        dto.setName("Device Name");
        dto.setUniqueIdentifier("ABC123");

        // Mapear el DTO a la entidad
        Device device = DeviceMapper.toEntity(dto);

        // Verificar que la entidad mapeada tiene los mismos valores que el DTO
        assertEquals(dto.getId(), device.getId());
        assertEquals(dto.getName(), device.getName());
        assertEquals(dto.getUniqueIdentifier(), device.getUniqueIdentifier());
    }

    // Test para el mapeo de Device a DeviceDto (toDto)
    @Test
    public void testToDto() {
        // Crear una entidad de prueba
        Device device = new Device();
        device.setId(1L);
        device.setName("Device Name");
        device.setUniqueIdentifier("ABC123");

        // Mapear la entidad a DTO
        DeviceDto dto = DeviceMapper.toDto(device);

        // Verificar que el DTO mapeado tiene los mismos valores que la entidad
        assertEquals(device.getId(), dto.getId());
        assertEquals(device.getName(), dto.getName());
        assertEquals(device.getUniqueIdentifier(), dto.getUniqueIdentifier());
    }
}
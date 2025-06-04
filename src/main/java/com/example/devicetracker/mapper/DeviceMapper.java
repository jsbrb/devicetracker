package com.example.devicetracker.mapper;

import com.example.devicetracker.dto.DeviceDto;
import com.example.devicetracker.model.Device;

public class DeviceMapper {

    //Convierte de DTO a entidad
    public static Device toEntity(DeviceDto dto){
        Device device = new Device();
        device.setId(dto.getId());
        device.setName(dto.getName());
        device.setUniqueIdentifier(dto.getUniqueIdentifier());
        return device;
    }

    //Convierte de Entidad a DTO
    public static DeviceDto toDto(Device device){
        DeviceDto dto = new DeviceDto();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setUniqueIdentifier(device.getUniqueIdentifier());
        return dto;
    }
}

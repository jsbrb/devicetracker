package com.example.devicetracker.mapper;

import com.example.devicetracker.dto.UserDto;
import com.example.devicetracker.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    //Convertir DTO a Entidad
    public static User toEntity (UserDto dto){
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        return user;
    }

    //Convertir Entidad a DTO
    public static UserDto toDto (User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    // Convertir lista de Entidades a lista de DTOs
    public static List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}

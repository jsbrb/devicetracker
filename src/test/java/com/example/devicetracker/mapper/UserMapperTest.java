package com.example.devicetracker.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.example.devicetracker.dto.UserDto;
import com.example.devicetracker.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class UserMapperTest {

    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp() {
        // Inicializamos los objetos necesarios para las pruebas
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    // Test para toEntity()
    @Test
    public void testToEntity() {
        // Convertimos el DTO a la entidad
        User entity = UserMapper.toEntity(userDto);

        // Verificamos que los valores de la entidad sean los mismos que los del DTO
        assertEquals(userDto.getId(), entity.getId());
        assertEquals(userDto.getName(), entity.getName());
        assertEquals(userDto.getEmail(), entity.getEmail());
    }

    // Test para toDto()
    @Test
    public void testToDto() {
        // Convertimos la entidad a un DTO
        UserDto dto = UserMapper.toDto(user);

        // Verificamos que los valores del DTO sean los mismos que los de la entidad
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
    }

    // Test para toDtoList()
    @Test
    public void testToDtoList() {
        // Creamos una lista de usuarios
        List<User> users = Arrays.asList(user);

        // Convertimos la lista de usuarios a una lista de DTOs
        List<UserDto> dtos = UserMapper.toDtoList(users);

        // Verificamos que la lista de DTOs tenga el tamaño correcto
        assertEquals(1, dtos.size());

        // Verificamos que el primer DTO tenga los mismos valores que la entidad
        UserDto dto = dtos.get(0);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
    }
}

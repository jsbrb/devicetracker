package com.example.devicetracker.controller;

import com.example.devicetracker.dto.UserDto;
import com.example.devicetracker.mapper.UserMapper;
import com.example.devicetracker.model.User;
import com.example.devicetracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//Configura Mockito con Junit5
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    //Simulamos el repositorio
    @Mock
    private UserRepository userRepository;

    //Instancia real del controlador, con repo "mockeado"
    @InjectMocks
    private UserController userController;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup(){
        user =new User();
        user.setId(1L);
        user.setName("Juan");
        user.setEmail("juan@prueba.com");

        userDto = UserMapper.toDto(user);
    }

    @Test
    void testCreateUser(){
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<UserDto> response = userController.create(userDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Juan", response.getBody().getName());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals("juan@prueba.com", response.getBody().get(0).getEmail());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByIdFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<UserDto> response = userController.getUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Juan", response.getBody().getName());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<UserDto> response = userController.getUser(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}



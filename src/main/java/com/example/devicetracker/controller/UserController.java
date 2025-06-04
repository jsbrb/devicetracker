package com.example.devicetracker.controller;

import com.example.devicetracker.dto.UserDto;
import com.example.devicetracker.mapper.UserMapper;
import com.example.devicetracker.model.User;
import com.example.devicetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Crear usuario
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(savedUser)); // 201
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(UserMapper.toDtoList(users)); // 200
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(UserMapper.toDto(user))) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto updateUserDto) {
        return userRepository.findById(id).map(user -> {
            user.setName(updateUserDto.getName());
            user.setEmail(updateUserDto.getEmail());
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(UserMapper.toDto(updatedUser)); // 200
        }).orElse(ResponseEntity.notFound().build()); // 404
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

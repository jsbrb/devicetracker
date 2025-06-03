package com.example.devicetracker.controller;

import com.example.devicetracker.model.User;
import com.example.devicetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Create
    @PostMapping
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }

    //Get
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }

    //Update
    @PutMapping("/{id}")


    //Delete
    @DeleteMapping("/{id}")
    public void delete (Long id){
        userRepository.deleteById(id);
    }
}

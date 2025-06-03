package com.example.devicetracker.repository;

import com.example.devicetracker.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findByDeviceIdOrderByTimestampDesc(Long deviceId);
}

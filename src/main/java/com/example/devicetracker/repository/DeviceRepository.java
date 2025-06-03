package com.example.devicetracker.repository;

import com.example.devicetracker.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}

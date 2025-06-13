package com.example.devicetracker.repository;

import com.example.devicetracker.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    /**
     * Busca un dispositivo por su identificador único.
     * @param uniqueIdentifier Identificador único del dispositivo
     * @return El dispositivo encontrado o null si no existe
     */
    Device findByUniqueIdentifier(String uniqueIdentifier);

    /**
     * Busca un dispositivo por su nombre.
     * @param name Nombre del dispositivo
     * @return El dispositivo encontrado o null si no existe
     */
    Device findByName(String name);

    /**
     * Cuenta la cantidad de dispositivos asociados a un usuario por su ID.
     * @param userId ID del usuario
     * @return Número de dispositivos asociados al usuario
     */
    long countByUserId(Long userId);
}
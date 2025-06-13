package com.example.devicetracker.repository;

import com.example.devicetracker.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    /**
     * Obtiene una lista de movimientos asociados a un dispositivo por su ID,
     * ordenados de más reciente a más antiguo.
     * @param deviceId ID del dispositivo
     * @return Lista de movimientos
     */
    List<Movement> findByDeviceIdOrderByTimestampDesc(Long deviceId);

    /**
     * Obtiene una lista de movimientos asociados a un dispositivo por su identificador único,
     * ordenados de más reciente a más antiguo.
     * @param uniqueIdentifier Identificador único del dispositivo
     * @return Lista de movimientos
     */
    List<Movement> findByDeviceUniqueIdentifierOrderByTimestampDesc(String uniqueIdentifier);

    /**
     * Obtiene una lista de movimientos asociados a un dispositivo por su nombre,
     * ordenados de más reciente a más antiguo.
     * @param name Nombre del dispositivo
     * @return Lista de movimientos
     */
    List<Movement> findByDeviceNameOrderByTimestampDesc(String name);

    /**
     * Cuenta la cantidad de movimientos asociados a un dispositivo por su ID.
     * @param deviceId ID del dispositivo
     * @return Número de movimientos
     */
    long countByDeviceId(Long deviceId);

    /**
     * Cuenta la cantidad de movimientos asociados a un dispositivo por su identificador único.
     * @param uniqueIdentifier Identificador único del dispositivo
     * @return Número de movimientos
     */
    long countByDeviceUniqueIdentifier(String uniqueIdentifier);

    /**
     * Cuenta la cantidad de movimientos asociados a un dispositivo por su nombre.
     * @param name Nombre del dispositivo
     * @return Número de movimientos
     */
    long countByDeviceName(String name);

}
package com.example.devicetracker.repository;

import com.example.devicetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Busca un usuario por su identificador único.
     * @param uniqueIdentifier Identificador único del usuario
     * @return El usuario encontrado o null si no existe
     */
    User findByUniqueIdentifier(String uniqueIdentifier);

    /**
     * Busca un usuario por su nombre.
     * @param name Nombre del usuario
     * @return El usuario encontrado o null si no existe
     */
    User findByName(String name);

    /**
     * Cuenta la cantidad de usuarios registrados en el sistema.
     * @return Número de usuarios registrados
     */
    long count();
}

package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.Coordinador;


public interface CoordinadorRepository extends JpaRepository<Coordinador, Integer> {
    @Query(
        value = "SELECT p.correo " +
                "FROM persona p " +
                "INNER JOIN coordinador c ON c.id_persona = p.id_persona " +
                "WHERE c.estado = true",
        nativeQuery = true
    )
    String obtenerCorreoCoordinador();

    @Query(
        value = "SELECT CONCAT(p.nombre, ' ', p.apellido) AS nombre_completo " +
                "FROM persona p " +
                "INNER JOIN coordinador c ON c.id_persona = p.id_persona " +
                "WHERE c.estado = true",
        nativeQuery = true
    )
    String obtenerNombreCompletoCoordinador();
}

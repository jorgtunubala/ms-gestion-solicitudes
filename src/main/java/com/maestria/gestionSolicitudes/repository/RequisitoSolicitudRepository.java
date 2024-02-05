package com.maestria.gestionSolicitudes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.RequisitoSolicitud;

public interface RequisitoSolicitudRepository extends JpaRepository<RequisitoSolicitud, Integer> {

    @Query("SELECT rs FROM RequisitoSolicitud rs WHERE rs.tipoSolicitud.codigo = ?1")
    Optional<RequisitoSolicitud> findByCodigo(String codigo);
}

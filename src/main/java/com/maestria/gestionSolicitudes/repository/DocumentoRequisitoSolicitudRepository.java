package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.DocumentoRequisitoSolicitud;

public interface DocumentoRequisitoSolicitudRepository extends JpaRepository<DocumentoRequisitoSolicitud, Integer> {
    
    @Query("SELECT drs FROM DocumentoRequisitoSolicitud drs " +
        "WHERE drs.requisitoSolicitud.id = ?1")
    List<DocumentoRequisitoSolicitud> findByRequisitoSolicitudId(Integer id);
}

package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    
}

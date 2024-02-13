package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.FirmaSolicitud;

public interface FirmaSolicitudRepository extends JpaRepository<FirmaSolicitud, Integer> {
    
}

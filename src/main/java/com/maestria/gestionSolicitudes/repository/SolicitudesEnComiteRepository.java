package com.maestria.gestionSolicitudes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.SolicitudesEnComite;

public interface SolicitudesEnComiteRepository extends JpaRepository<SolicitudesEnComite, Integer> {
    
    Optional<SolicitudesEnComite> findBySolicitud(Solicitudes solicitud);
}

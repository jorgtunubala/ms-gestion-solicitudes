package com.maestria.gestionSolicitudes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.SolicitudesEnConcejo;

public interface SolicitudesEnConcejoRepository extends JpaRepository<SolicitudesEnConcejo, Integer> {
    
    Optional<SolicitudesEnConcejo> findBySolicitud(Solicitudes solicitud);
}

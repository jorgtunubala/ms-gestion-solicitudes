package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.SolicitudBeca;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface SolicitudBecaRepository extends JpaRepository<SolicitudBeca, Integer>{
    
    SolicitudBeca findBySolicitud(Solicitudes solicitud);
}

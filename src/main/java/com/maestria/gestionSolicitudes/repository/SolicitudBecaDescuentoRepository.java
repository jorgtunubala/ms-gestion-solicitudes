package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.SolicitudBecaDescuento;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface SolicitudBecaDescuentoRepository extends JpaRepository<SolicitudBecaDescuento, Integer>{
    
    SolicitudBecaDescuento findBySolicitud(Solicitudes solicitud);
}

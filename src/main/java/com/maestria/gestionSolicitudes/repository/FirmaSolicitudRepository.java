package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.FirmaSolicitud;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface FirmaSolicitudRepository extends JpaRepository<FirmaSolicitud, Integer> {
    FirmaSolicitud findBySolicitud(Solicitudes solicitud);
}

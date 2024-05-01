package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AvalSeminarioActualizacion;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface AvalSeminarioActRepository extends JpaRepository<AvalSeminarioActualizacion, Integer> {
    AvalSeminarioActualizacion findBySolicitud(Solicitudes solicitud);
}

package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AplazarSemestre;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface AplazarSemestreRepository extends JpaRepository<AplazarSemestre, Integer> {
    AplazarSemestre findBySolicitud(Solicitudes solicitud);
}

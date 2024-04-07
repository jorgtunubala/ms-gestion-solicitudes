package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.CursarAsignatura;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface CursarAsignaturaRepository extends JpaRepository<CursarAsignatura, Integer> {
    CursarAsignatura findBySolicitud(Solicitudes solicitud);
}

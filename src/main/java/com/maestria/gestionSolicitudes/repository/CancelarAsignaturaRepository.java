package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.CancelarAsignatura;
import com.maestria.gestionSolicitudes.domain.Solicitudes;


public interface CancelarAsignaturaRepository extends JpaRepository<CancelarAsignatura, Integer> {
    CancelarAsignatura findBySolicitud(Solicitudes solicitud);
}

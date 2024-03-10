package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AdicionarAsignatura;
import com.maestria.gestionSolicitudes.domain.Solicitudes;


public interface AdicionarAsignaturaRepository extends JpaRepository<AdicionarAsignatura, Integer> {
    AdicionarAsignatura findBySolicitud(Solicitudes solicitud);
}

package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maestria.gestionSolicitudes.domain.HistorialEstadoSolicitudes;

@Repository
public interface HistorialEstadoSolicitudesRepository extends JpaRepository<HistorialEstadoSolicitudes, Integer> {
    
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maestria.gestionSolicitudes.domain.HistorialEstadoSolicitudes;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

@Repository
public interface HistorialEstadoSolicitudesRepository extends JpaRepository<HistorialEstadoSolicitudes, Integer> {
    List<HistorialEstadoSolicitudes> findBySolicitudOrderByFechaCreacionAsc(Solicitudes solicitud);
}

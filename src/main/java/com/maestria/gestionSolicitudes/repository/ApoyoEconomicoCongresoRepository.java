package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoCongreso;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface ApoyoEconomicoCongresoRepository extends JpaRepository<ApoyoEconomicoCongreso, Integer> {
    ApoyoEconomicoCongreso findBySolicitud(Solicitudes solicitud);
}

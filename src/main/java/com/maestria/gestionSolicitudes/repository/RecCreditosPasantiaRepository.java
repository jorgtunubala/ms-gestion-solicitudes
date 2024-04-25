package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.RecCreditosPasantia;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface RecCreditosPasantiaRepository extends JpaRepository<RecCreditosPasantia, Integer> {
    RecCreditosPasantia findBySolicitud(Solicitudes solicitud);
}

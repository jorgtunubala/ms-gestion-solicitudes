package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.RecCreditosDisCurricular;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface RecCreditosDisCurricularRepository extends JpaRepository<RecCreditosDisCurricular, Integer> {
    RecCreditosDisCurricular findBySolicitud(Solicitudes solicitud);
}

package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AvalPasantiaInvestigacion;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface AvalPasantiaInvestigacionRepository extends JpaRepository<AvalPasantiaInvestigacion, Integer> {
    AvalPasantiaInvestigacion findBySolicitud(Solicitudes solicitud);
}

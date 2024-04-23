package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoInvestigacion;
import com.maestria.gestionSolicitudes.domain.DocumentosApoyoEconomico;

public interface DocumentosApoyoEconomicoRepository extends JpaRepository<DocumentosApoyoEconomico, Integer> {
    List<DocumentosApoyoEconomico> findAllByApoyoEconomicoInvestigacion(ApoyoEconomicoInvestigacion apoyoEconomicoInvestigacion);
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.ApoyoEconomicoCongreso;
import com.maestria.gestionSolicitudes.domain.DocumentosApoyoEconomicoCongreso;

public interface DocumentosApoyoEconomicoCongresoRepository extends JpaRepository<DocumentosApoyoEconomicoCongreso, Integer> {
    List<DocumentosApoyoEconomicoCongreso> findAllByApoyoEconomicoCongreso(ApoyoEconomicoCongreso apoyoEconomicoInvestigacion);
}
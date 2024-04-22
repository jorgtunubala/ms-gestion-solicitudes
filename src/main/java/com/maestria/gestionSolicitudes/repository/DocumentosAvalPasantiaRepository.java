package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AvalPasantiaInvestigacion;
import com.maestria.gestionSolicitudes.domain.DocumentosAvalPasantia;

public interface DocumentosAvalPasantiaRepository extends JpaRepository<DocumentosAvalPasantia, Integer> {
    List<DocumentosAvalPasantia> findAllByAvalPasantia(AvalPasantiaInvestigacion avalPasantia);
}

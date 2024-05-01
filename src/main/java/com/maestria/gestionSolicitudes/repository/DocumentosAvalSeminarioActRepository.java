package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.DocumentosAvalSeminarioAct;
import com.maestria.gestionSolicitudes.domain.AvalSeminarioActualizacion;

public interface DocumentosAvalSeminarioActRepository extends JpaRepository<DocumentosAvalSeminarioAct, Integer> {
    List<DocumentosAvalSeminarioAct> findAllByAvalSeminarioActualizacion(AvalSeminarioActualizacion recCreditosPasantia);
}

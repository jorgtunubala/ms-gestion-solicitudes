package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.DocumentosRecCreditosPasantia;
import com.maestria.gestionSolicitudes.domain.RecCreditosPasantia;

public interface DocumentosRecCreditosPasantiaRepository extends JpaRepository<DocumentosRecCreditosPasantia, Integer> {
    List<DocumentosRecCreditosPasantia> findAllByRecCreditosPasantia(RecCreditosPasantia recCreditosPasantia);
}

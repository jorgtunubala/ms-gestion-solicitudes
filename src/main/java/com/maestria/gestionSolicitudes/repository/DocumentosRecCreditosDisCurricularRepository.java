package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.DocumentosRecCreditosDisCurricular;
import com.maestria.gestionSolicitudes.domain.RecCreditosDisCurricular;

public interface DocumentosRecCreditosDisCurricularRepository extends JpaRepository<DocumentosRecCreditosDisCurricular, Integer> {
    List<DocumentosRecCreditosDisCurricular> findAllByRecCreditosDisCurricular(RecCreditosDisCurricular recCreditosDisCurricular);
}

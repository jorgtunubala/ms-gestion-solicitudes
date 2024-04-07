package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.CursarAsignatura;
import com.maestria.gestionSolicitudes.domain.DocumentosCursarAsignatura;

public interface DocumentosCursarAsignaturaRepository extends JpaRepository<DocumentosCursarAsignatura, Integer> {
    List<DocumentosCursarAsignatura> findAllByCursarAsignatura(CursarAsignatura homologacion);
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.CursarAsignatura;
import com.maestria.gestionSolicitudes.domain.DatosCursarAsignatura;

public interface DatosCursarAsignaturaRepository extends JpaRepository<DatosCursarAsignatura, Integer> {
    List<DatosCursarAsignatura> findAllByCursarAsignatura(CursarAsignatura cursarAsignatura);
}

package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AdicionarAsignatura;

public interface AdicionarAsignaturaRepository extends JpaRepository<AdicionarAsignatura, Integer> {
    
}

package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AsignaturaAdicionada;

public interface AsignaturaAdicionadaRepository extends JpaRepository<AsignaturaAdicionada, Integer> {
    
}

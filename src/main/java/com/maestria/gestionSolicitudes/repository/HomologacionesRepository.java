package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.Homologaciones;

public interface HomologacionesRepository extends JpaRepository<Homologaciones, Integer> {
    
}

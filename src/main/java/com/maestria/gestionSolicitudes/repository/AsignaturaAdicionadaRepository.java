package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AdicionarAsignatura;
import com.maestria.gestionSolicitudes.domain.AsignaturaAdicionada;

public interface AsignaturaAdicionadaRepository extends JpaRepository<AsignaturaAdicionada, Integer> {
    
    List<AsignaturaAdicionada> findByAdicionarAsignatura(AdicionarAsignatura adicionarAsignatura);
}

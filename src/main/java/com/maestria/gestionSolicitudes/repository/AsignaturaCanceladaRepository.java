package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AsignaturaCancelada;
import com.maestria.gestionSolicitudes.domain.CancelarAsignatura;

public interface AsignaturaCanceladaRepository extends JpaRepository<AsignaturaCancelada, Integer> {
    
    List<AsignaturaCancelada> findByCancelarAsignatura(CancelarAsignatura cancelarAsignatura);
}

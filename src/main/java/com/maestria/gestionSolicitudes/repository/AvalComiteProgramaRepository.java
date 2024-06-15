package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.AvalComitePrograma;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface AvalComiteProgramaRepository 
        extends JpaRepository<AvalComitePrograma, Integer>{
    
    List<AvalComitePrograma> findBySolicitud(Solicitudes solicitud);
}

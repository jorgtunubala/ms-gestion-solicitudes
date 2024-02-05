package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.TiposSolicitud;

public interface TiposSolicitudRepository extends JpaRepository<TiposSolicitud, Integer> {
    
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.TiposSolicitud;

public interface TiposSolicitudRepository extends JpaRepository<TiposSolicitud, Integer> {

    List<TiposSolicitud> findByEstadoOrderByNombreAsc(String estado);
    TiposSolicitud findByCodigo(String codigo);
}

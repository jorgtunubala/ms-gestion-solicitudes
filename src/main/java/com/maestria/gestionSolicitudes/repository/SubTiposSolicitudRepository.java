package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.SubTiposSolicitud;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;

public interface SubTiposSolicitudRepository extends JpaRepository<SubTiposSolicitud, Integer> {

    List<SubTiposSolicitud> findByTipoSolicitud(TiposSolicitud tiposSolicitud);

    SubTiposSolicitud findByCodigo(String codigo);
}

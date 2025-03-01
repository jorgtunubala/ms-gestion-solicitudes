package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.TiposSolicitud;

public interface TiposSolicitudRepository extends JpaRepository<TiposSolicitud, Integer> {

    @Query(value = """
        SELECT id, fecha_creacion, fecha_modificacion, usuario_creacion,
        usuario_modificacion, codigo, estado, nombre, fecha_inicio, fecha_final
        FROM tipos_solicitudes
        """, nativeQuery = true )
    List<TiposSolicitud> findByEstadoOrderByNombreAsc(String estado);

    TiposSolicitud findByCodigo(String codigo);
}

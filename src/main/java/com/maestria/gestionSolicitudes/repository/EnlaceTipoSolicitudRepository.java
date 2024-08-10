package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.maestria.gestionSolicitudes.domain.EnlaceTipoSolicitud;
public interface EnlaceTipoSolicitudRepository extends JpaRepository<EnlaceTipoSolicitud, Integer> {
    
    @Query("SELECT ets FROM EnlaceTipoSolicitud ets " +
        "WHERE ets.tiposSolicitud.id = ?1")
    List<EnlaceTipoSolicitud> findByTiposSolicitud(Integer id);
}


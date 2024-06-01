package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.EnlacesSubtipos;

public interface EnlaceSubtipoRepository extends JpaRepository<EnlacesSubtipos, Integer> {
    
    @Query("SELECT es FROM EnlacesSubtipos es " +
        "WHERE es.subTiposSolicitud.id = ?1")
    List<EnlacesSubtipos> findBySubtipoSolicitud(Integer id);
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.DocumentosSubtipos;

public interface DocumentoSubtipoRepository extends JpaRepository<DocumentosSubtipos, Integer> {
    
    @Query("SELECT ds FROM DocumentosSubtipos ds " +
        "WHERE ds.subTiposSolicitud.id = ?1")
    List<DocumentosSubtipos> findBySubtipoSolicitud(Integer id);
}

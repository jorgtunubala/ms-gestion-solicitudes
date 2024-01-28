package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maestria.gestionSolicitudes.domain.DocumentosAdjuntosHomologacion;
import com.maestria.gestionSolicitudes.domain.Homologaciones;

public interface DocumentosAdjuntosHomologacionRepository extends JpaRepository<DocumentosAdjuntosHomologacion, Integer> {
    
    @Query("""
            SELECT dah.documento FROM DocumentosAdjuntosHomologacion dah 
            WHERE dah.homologacion = :homologacion
            """)
    List<String> findDocumentosByHomologacion(@Param("homologacion") Homologaciones homologacion);
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Integer> {
    
    @Query("""
        SELECT h FROM Solicitudes h 
        WHERE h.idTutor = ?1
        ORDER BY h.fechaCreacion ASC
        """)           
    List<Solicitudes> findAllByIdTutorOrderByFechaCreacionAsc(Integer idTutor);
}

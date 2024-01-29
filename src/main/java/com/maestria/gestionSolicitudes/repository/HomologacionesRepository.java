package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.Homologaciones;

public interface HomologacionesRepository extends JpaRepository<Homologaciones, Integer> {
    
    @Query("""
        SELECT h FROM Homologaciones h 
        INNER JOIN h.solicitud s
        WHERE h.idTutor = ?1
        ORDER BY h.fechaCreacion ASC
        """)           
    List<Homologaciones> findAllByIdTutorOrderByFechaCreacionAsc(Integer idTutor);
}

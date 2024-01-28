package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.Homologaciones;

public interface HomologacionesRepository extends JpaRepository<Homologaciones, Integer> {
    
    @Query("""
        SELECT h FROM Homologaciones h 
        INNER JOIN h.solicitud s
        ORDER BY h.fechaCreacion ASC
        """)           
    List<Homologaciones> findAllByOrderByFechaCreacionAsc();
}

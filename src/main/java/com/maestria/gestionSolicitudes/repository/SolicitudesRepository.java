package com.maestria.gestionSolicitudes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Integer> {
    
    @Query("""
        SELECT s FROM Solicitudes s 
        WHERE s.idTutor = ?1
        AND s.estado = ?2
        ORDER BY s.fechaCreacion ASC
        """)           
    List<Solicitudes> findAllByIdTutorOrderByFechaCreacionAsc(Integer idTutor, String estado);

    Optional<Solicitudes> findByRadicado(String radicado);
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Integer> {
    
    @Query("""
        SELECT s FROM Solicitudes s 
        inner join FirmaSolicitud fs on fs.solicitud.id = s.id 
        WHERE s.estado = ?2
        AND (s.idTutor = ?1 AND fs.firmaTutor = false) 
        OR (s.idDirector = ?1 AND fs.firmaDirector = false)        
        ORDER BY s.fechaModificacion ASC
        """)           
    List<Solicitudes> findAllByIdTutorOrderByFechaModificacionAsc(Integer idTutor, String estado);

    Optional<Solicitudes> findByRadicado(String radicado);

    List<Solicitudes> findByEstadoOrderByFechaModificacionAsc(String estado);
}

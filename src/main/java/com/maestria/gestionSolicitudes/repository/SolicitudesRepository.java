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

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
                   "FROM solicitudes s " +
                   "INNER JOIN docentes d ON d.id = s.id_director " +
                   "INNER JOIN personas p ON p.id = d.id_persona " +
                   "WHERE s.id = ?1 AND p.correo_electronico = ?2", nativeQuery = true)
    Integer obtenerDirectorSolicitud(Integer  solicitudId, String correoElectronico);

    @Query(value = """
        SELECT s.id, s.id_estudiante, 
                s.documento_firmado, 
               s.fecha_creacion, s.fecha_modificacion, s.estado 
        FROM solicitudes s 
        ORDER BY s.fecha_modificacion DESC
        """, nativeQuery = true)
    List<Solicitudes> findAllSolicitudesOrderByFechaModificacion();

    
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.maestria.gestionSolicitudes.domain.SolicitudesCertificadoVotacion;
import com.maestria.gestionSolicitudes.dto.rest.response.SolicitudCertificadoVotacionResponse;

@Repository
public interface SolicitudesCertificadoVotacionRepository extends JpaRepository<SolicitudesCertificadoVotacion, Integer> {
    @Query(value = """
        SELECT s.id, s.id_tipo_solicitud, s.id_estudiante, s.documento_firmado,
               s.fecha_creacion, s.fecha_modificacion, s.estado 
        FROM solicitudes s  where s.id_tipo_solicitud = 32 
        ORDER BY s.fecha_modificacion desc
        """, nativeQuery = true )
    List<SolicitudesCertificadoVotacion> findAllSolicitudesOrderByFechaModificacion();


}
package com.maestria.gestionSolicitudes.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.jpa.repository.JpaRepository;
import com.maestria.gestionSolicitudes.domain.DocumentosCertificadoVotacion;
import org.springframework.data.repository.query.Param;

@Repository
public interface DocumentoCertificadoVotacionRepository extends JpaRepository<DocumentosCertificadoVotacion, Integer> {

    @Query(value = """
        SELECT s.id, s.documento_firmado
        FROM solicitudes s 
        JOIN estudiantes e ON s.id_estudiante = e.id
        WHERE s.estado = :estadoSolicitud
        AND e.estado_maestria = :estadoEstudiante
        ORDER BY s.fecha_modificacion DESC
        """, nativeQuery = true)
    List<DocumentosCertificadoVotacion> findAllDocumentosAprobadosDeEstudiantesActivos(
        @Param("estadoSolicitud") String estadoSolicitud, 
        @Param("estadoEstudiante") String estadoEstudiante);

}


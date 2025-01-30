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
        WHERE s.estado = "Aprobada"
        ORDER BY s.fecha_modificacion DESC
        """, nativeQuery = true)
    List<DocumentosCertificadoVotacion> findAllDocmentosSolicitudesCer_votOrderByFechaModificacion();

    @Query(value = """
        SELECT s.id, s.documento_firmado
        FROM solicitudes s 
        INNER JOIN estudiantes e ON s.id_estudiante = e.id
        WHERE s.estado = 'Aprobada'
        AND e.periodo_ingreso = :period
        AND s.id IN (:ids)
        ORDER BY s.fecha_modificacion DESC
        """, nativeQuery = true)
    List<DocumentosCertificadoVotacion> findByPeriodAndIds(
        @Param("period") String period, 
        @Param("ids") List<Integer> ids
    );

    @Query(value = """
        SELECT s.id, s.documento_firmado
        FROM solicitudes s 
        INNER JOIN estudiantes e ON s.id_estudiante = e.id
        WHERE s.estado = 'Aprobada'
        AND e.periodo_ingreso = :period
        ORDER BY s.fecha_modificacion DESC
        """, nativeQuery = true)
    List<DocumentosCertificadoVotacion> findByPeriod(@Param("period") String period);

    @Query(value = """
        SELECT s.id, s.documento_firmado
        FROM solicitudes s 
        WHERE s.estado = 'Aprobada'
        AND s.id IN (:ids)
        ORDER BY s.fecha_modificacion DESC
        """, nativeQuery = true)
    List<DocumentosCertificadoVotacion> findByIds(@Param("ids") List<Integer> ids);
}


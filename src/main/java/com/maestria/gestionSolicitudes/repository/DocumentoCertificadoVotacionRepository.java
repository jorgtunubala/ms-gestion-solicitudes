package com.maestria.gestionSolicitudes.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import com.maestria.gestionSolicitudes.domain.DocumentosCertificadoVotacion;

@Repository
public interface DocumentoCertificadoVotacionRepository extends JpaRepository<DocumentosCertificadoVotacion, Integer> {
    @Query(value = """
        SELECT s.id, s.documento_firmado as documento_firmado
        FROM solicitudes s WHERE s.estado = "Aprobada"
        ORDER BY s.fecha_modificacion desc
        """, nativeQuery = true)
        List<DocumentosCertificadoVotacion> findAllDocmentosSolicitudesCer_votOrderByFechaModificacion();
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.maestria.gestionSolicitudes.domain.NotaDocumentoRequerido;

public interface NotaDocumentoRequeridoRepository extends JpaRepository<NotaDocumentoRequerido, Integer> {
    @Query("SELECT ndr FROM NotaDocumentoRequerido ndr " +
        "WHERE ndr.requisitoSolicitud.id = ?1")
    List<NotaDocumentoRequerido> findByRequisitoSolicitudId(Integer id);
}

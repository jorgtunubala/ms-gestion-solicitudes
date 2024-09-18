package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.DocumentosConcejo;
import com.maestria.gestionSolicitudes.domain.SolicitudesEnConcejo;

public interface DocumentosConcejoRepository extends JpaRepository<DocumentosConcejo, Integer> {
    
    List<DocumentosConcejo> findBySolicitudConcejo(SolicitudesEnConcejo solicitudEnConcejo);
}

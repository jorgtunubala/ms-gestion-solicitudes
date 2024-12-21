package com.maestria.gestionSolicitudes.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.maestria.gestionSolicitudes.domain.SolicitudesCertificadoVotacion;

public interface SolicitudesCertificadoVotacionRepository extends JpaRepository<SolicitudesCertificadoVotacion, Integer> {
    Optional<SolicitudesCertificadoVotacion> findById(Integer id);
}
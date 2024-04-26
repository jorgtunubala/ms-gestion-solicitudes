package com.maestria.gestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.ReconocimientoCreditos;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface ReconocimientoCreditosRepository extends JpaRepository<ReconocimientoCreditos, Integer> {
    ReconocimientoCreditos findBySolicitudAndTipoReconocimiento(Solicitudes solicitud, String tipoReconocimiento);
}

package com.maestria.gestionSolicitudes.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaDto;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaResponseDto;

@FeignClient(value="gestionAsignaturasService", url="${gestion-asignaturas.url}")
public interface GestionAsignaturasService {
    
    @PostMapping("${gestion-asignaturas.resources.crear-asignaturas-externas}")
    AsignaturaExternaResponseDto registrarAsignaturasExternas(AsignaturaExternaDto asignatura);

    @GetMapping("${gestion-asignaturas.resources.obtener-asignatura}")
    AsignaturaExternaResponseDto obtenerAsignaturaExterna(@PathVariable Integer idAsignatura);
}

package com.maestria.gestionSolicitudes.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaDto;
import com.maestria.gestionSolicitudes.dto.client.AsignaturaExternaResponseDto;

@FeignClient(value="gestionAsignaturasService", url="${gestion-asignaturas.url}")
public interface GestionAsignaturasService {
    
    @PostMapping("${gestion-asignaturas.resources.crear-asignaturas-externas}")
    AsignaturaExternaResponseDto registrarAsignaturasExternas(AsignaturaExternaDto asignatura);
}

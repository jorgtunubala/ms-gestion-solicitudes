package com.maestria.gestionSolicitudes.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.maestria.gestionSolicitudes.dto.rest.InformacionPersonalDto;

@FeignClient(value="gestionDocentesEstudiantes", url="${gestion-estudiantes-docentes.url}")
public interface GestionDocentesEstudiantesService {
    
    @GetMapping("${gestion-estudiantes-docentes.resources.docentes-activos}")
    List<InformacionPersonalDto> obtenerDocentesActivos(@PathVariable String estado);

    @GetMapping("${gestion-estudiantes-docentes.resources.obtener-estudiante}")
    InformacionPersonalDto obtenerInformacionEstudiante(@PathVariable String correo);
}

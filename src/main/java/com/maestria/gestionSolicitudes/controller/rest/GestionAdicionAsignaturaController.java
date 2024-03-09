package com.maestria.gestionSolicitudes.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.rest.request.TipoSolicitudDto;
import com.maestria.gestionSolicitudes.dto.rest.response.InfoAdicionAsignaturaResponse;
import com.maestria.gestionSolicitudes.dto.rest.response.TipoSolicitudResponse;
import com.maestria.gestionSolicitudes.service.rest.AdicionAsignaturaService;

@RestController
@RequestMapping("/gestionAdicionAsignaturas")
public class GestionAdicionAsignaturaController {
    
    @Autowired
    private AdicionAsignaturaService adicionAsignaturaService;

    @GetMapping("/lista-asignaturas-adicionar")
    public List<InfoAdicionAsignaturaResponse> listarAsignaturasAdicionar() {
        return adicionAsignaturaService.listarDocentesAsignaturas();
    }
}

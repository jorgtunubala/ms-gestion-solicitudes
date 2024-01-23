package com.maestria.gestionSolicitudes.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.rest.request.DatosSolicitudHomologacionDto;
import com.maestria.gestionSolicitudes.service.rest.SolicitudesHomologacionService;

@RestController
@RequestMapping("/gestionSolicitudHomologacion")
public class GestionSolicitudesHomologacionController {
    @Autowired
    private SolicitudesHomologacionService solicitudesHomologacionService;

    @PostMapping
    public Boolean registrarSolicitudHomologacion(@RequestBody DatosSolicitudHomologacionDto dHomologacionDto) {
        return solicitudesHomologacionService.registrarSolicitudHomologacion(dHomologacionDto);
    }
}

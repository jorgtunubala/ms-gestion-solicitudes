package com.maestria.gestionSolicitudes.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.rest.request.DatosSolicitudHomologacionDto;
import com.maestria.gestionSolicitudes.dto.rest.response.DatosSolicitudHomologacion;
import com.maestria.gestionSolicitudes.service.rest.SolicitudesHomologacionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/gestionSolicitudHomologacion")
public class GestionSolicitudesHomologacionController {
    @Autowired
    private SolicitudesHomologacionService solicitudesHomologacionService;

    @PostMapping("/save")
    public Boolean registrarSolicitudHomologacion(@RequestBody DatosSolicitudHomologacionDto dHomologacionDto) {
        return solicitudesHomologacionService.registrarSolicitudHomologacion(dHomologacionDto);
    }

    @GetMapping("/obtener-homologaciones/{identificador}")
    public List<DatosSolicitudHomologacion> obtenerTodasHomologaciones(@PathVariable String identificador) {
        return solicitudesHomologacionService.obtenerTodasHomologaciones(identificador);
    }
    
}

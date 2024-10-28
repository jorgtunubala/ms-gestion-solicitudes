package com.maestria.gestionSolicitudes.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.rest.response.Rolinformacion.RolInformacionResponse;
import com.maestria.gestionSolicitudes.service.rest.RolInformacionService;

@RestController
@RequestMapping("/gestion/rol-informacion")
public class RolInformacionController {

    @Autowired
    private RolInformacionService rolInformacionService;

    // Endpoint para obtener información del rol por parte del cargo
    @GetMapping("/buscar")
    public RolInformacionResponse obtenerRolInformacion(@RequestParam String cargo) {
        return rolInformacionService.obtenerRolInformacion(cargo);
    }

    // Endpoint para guardar o actualizar información del rol
    @PostMapping("/guardar")
    public Boolean guardarRolInformacion(@RequestBody RolInformacionResponse rolInfo) {
        return rolInformacionService.guardarRolInformacion(rolInfo);
    }
    
}
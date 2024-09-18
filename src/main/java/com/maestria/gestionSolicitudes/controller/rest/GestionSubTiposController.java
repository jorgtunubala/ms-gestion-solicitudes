package com.maestria.gestionSolicitudes.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maestria.gestionSolicitudes.dto.rest.response.SubTiposSolicitudResponse;
import com.maestria.gestionSolicitudes.service.rest.GestionSubTiposSolicitudService;

@RestController
@RequestMapping("/gestionSubtipos")
public class GestionSubTiposController {
    
    @Autowired
    private GestionSubTiposSolicitudService gestionSubTiposSolicitudService;

    @GetMapping("/subTiposSolicitud")
    public List<SubTiposSolicitudResponse> obtenerSubTiposSolicitud() {
        List<SubTiposSolicitudResponse> response = new ArrayList<>();
        try {
            response = gestionSubTiposSolicitudService.obtenerSubtiposPorTipoSolicitud();
        } catch (Exception e) {
            e.getMessage();
        }
        return response;
    }
}

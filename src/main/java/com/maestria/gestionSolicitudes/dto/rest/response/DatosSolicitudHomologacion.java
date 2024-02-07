package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import lombok.Data;

@Data
public class DatosSolicitudHomologacion {
    private String programaProcedencia;
    private String institutoProcedencia;
    private List<DatosAsignaturaHomologar> datosAsignatura;
    private List<String> documentosAdjuntos;
    private String estadoSolicitud;
}

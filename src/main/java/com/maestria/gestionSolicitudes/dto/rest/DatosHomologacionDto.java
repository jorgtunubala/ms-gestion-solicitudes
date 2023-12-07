package com.maestria.gestionSolicitudes.dto.rest;

import java.util.List;

import lombok.Data;

@Data
public class DatosHomologacionDto {
    private String programaProcedencia;
    private String institucionProcedencia;
    private List<DatosAsignaturaHomologacionDto> listaAsignaturas;
}

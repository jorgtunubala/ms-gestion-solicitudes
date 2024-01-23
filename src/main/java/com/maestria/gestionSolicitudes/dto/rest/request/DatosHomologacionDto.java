package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class DatosHomologacionDto {
    private String programaProcedencia;
    private String institucionProcedencia;
    private List<DatosAsignaturaHomologacionDto> listaAsignaturas;
}

package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class CancelarAsignaturaRequest {
    private List<InfoAdicionAsignaturaRequest> listaAsignaturas;
    private String motivo;
}

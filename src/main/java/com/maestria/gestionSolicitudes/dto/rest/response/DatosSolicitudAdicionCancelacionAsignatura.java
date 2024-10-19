package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import lombok.Data;

@Data
public class DatosSolicitudAdicionCancelacionAsignatura {
    private List<InfoAdicionCancelacion> listaAsignaturas;
    private String motivo;
    private String documentoAdjunto;
}

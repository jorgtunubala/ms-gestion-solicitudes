package com.maestria.gestionSolicitudes.dto.rest.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ApoyoEconomicoPublicacionEventoRequest {
    private String nombreEvento;
    private String tipoEvento;
    private String fechaInicio;
    private String fechaFin;
    private Integer idDirectorGrupo;
    private String nombreDirectorGrupo;
    private String tituloPublicacion;
    private BigDecimal valorApoyo;
    private String entidadBancaria;
    private String tipoCuenta;
    private String numeroCuenta;
    private String numeroCedulaAsociada;
    private String direccionResidencia;
    private String finalidadApoyo;
    private String grupoInvestigacion;
    private List<String> documentosAdjuntos;
}

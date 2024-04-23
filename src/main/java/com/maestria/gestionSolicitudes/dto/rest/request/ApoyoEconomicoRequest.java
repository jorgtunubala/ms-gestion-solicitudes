package com.maestria.gestionSolicitudes.dto.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApoyoEconomicoRequest {
    private String lugarPasantia;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    private Integer idDirectorGrupo;
    private String grupoInvestigacion;
    private BigDecimal valorApoyo;
    private String entidadBancaria;
    private String tipoCuenta;
    private String numeroCuenta;
    private String numeroCedulaAsociada;
    private String direccionResidencia;
    private List<String> documentosAdjuntos;
}

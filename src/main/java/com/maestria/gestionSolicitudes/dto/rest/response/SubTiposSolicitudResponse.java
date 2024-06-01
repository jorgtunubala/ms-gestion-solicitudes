package com.maestria.gestionSolicitudes.dto.rest.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class SubTiposSolicitudResponse {

    private Integer id;
    private Integer idTipoSolicitud;
    private String codigo;
    private String nombre;
    private String abreviatura;
    private BigDecimal peso;
    private Integer horasAsignadas;
    private List<String> documentos;
    private List<String> enlaces;
}

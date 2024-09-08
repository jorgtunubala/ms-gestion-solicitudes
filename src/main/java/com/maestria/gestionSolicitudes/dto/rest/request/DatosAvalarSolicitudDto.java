package com.maestria.gestionSolicitudes.dto.rest.request;

import lombok.Data;

@Data
public class DatosAvalarSolicitudDto {
    private Integer idSolicitud;
    private Boolean firmaTutor;
    private Boolean firmaDirector;
    private String documentoPdfSolicitud;
}

package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentosRequeridosResponse extends RespuestaBase {
    
    private DocumentoRequeridoSolicitudDto doRequeridoSolicitudDto;
}

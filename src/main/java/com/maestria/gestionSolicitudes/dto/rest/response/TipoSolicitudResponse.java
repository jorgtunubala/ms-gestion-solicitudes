package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import com.maestria.gestionSolicitudes.dto.rest.request.TipoSolicitudDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TipoSolicitudResponse extends RespuestaBase {
    
    private List<TipoSolicitudDto> tipoSolicitudDto;
}

package com.maestria.gestionSolicitudes.dto.rest.response;

import com.maestria.gestionSolicitudes.dto.rest.InformacionPersonalDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InformacionPersonalResponse extends RespuestaBase {
    private InformacionPersonalDto informacionPersonalDto;
}

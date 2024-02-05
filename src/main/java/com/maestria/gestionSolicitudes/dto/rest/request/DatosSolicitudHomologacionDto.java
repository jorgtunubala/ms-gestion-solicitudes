package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DatosSolicitudHomologacionDto {
    private DatosHomologacionDto datosHomologacionDto;
    private List<String> documentosAdjuntos;
}

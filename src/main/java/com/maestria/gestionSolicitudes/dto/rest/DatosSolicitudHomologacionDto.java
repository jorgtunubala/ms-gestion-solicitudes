package com.maestria.gestionSolicitudes.dto.rest;

import lombok.Data;

@Data
public class DatosSolicitudHomologacionDto {
    private InformacionPersonalDto informacionPersonalDto;
    private DatosHomologacionDto datosHomologacionDto;
    private TutorDto tutorDto;
}

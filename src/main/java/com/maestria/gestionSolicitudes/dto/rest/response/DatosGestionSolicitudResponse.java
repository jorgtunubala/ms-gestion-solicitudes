package com.maestria.gestionSolicitudes.dto.rest.response;

import lombok.Data;

@Data
public class DatosGestionSolicitudResponse {
    
    private DatosComunSolicitud datosComunSolicitud;
    private DatosSolicitudHomologacion datosSolicitudHomologacion;
    private DatosSolicitudAdicionCancelacionAsignatura dAdicionCancelacionAsignatura;
    private DatosSolicitudAplazarSemestre datosSolicitudAplazarSemestre;
    private DatosSolicitudCursarAsignaturas datosSolicitudCursarAsignaturas;
    private AvalPasantiaInvResponse datoAvalPasantiaInv;
}

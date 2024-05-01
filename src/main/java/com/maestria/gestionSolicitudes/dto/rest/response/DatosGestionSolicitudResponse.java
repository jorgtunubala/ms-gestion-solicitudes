package com.maestria.gestionSolicitudes.dto.rest.response;

import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoCongresoRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoPublicacionEventoRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.ApoyoEconomicoRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.AvalSeminarioActRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.ReconocimientoCreditosRequest;

import lombok.Data;

@Data
public class DatosGestionSolicitudResponse {
    
    private DatosComunSolicitud datosComunSolicitud;
    private DatosSolicitudHomologacion datosSolicitudHomologacion;
    private DatosSolicitudAdicionCancelacionAsignatura dAdicionCancelacionAsignatura;
    private DatosSolicitudAplazarSemestre datosSolicitudAplazarSemestre;
    private DatosSolicitudCursarAsignaturas datosSolicitudCursarAsignaturas;
    private AvalPasantiaInvResponse datoAvalPasantiaInv;
    private ApoyoEconomicoRequest datosApoyoEconomico;
    private ReconocimientoCreditosRequest datosReconocimientoCreditos;
    private AvalSeminarioActRequest datosAvalSeminario;
    private ApoyoEconomicoCongresoRequest datosApoyoEconomicoCongreso;
    private ApoyoEconomicoPublicacionEventoRequest datosApoyoEconomicoPublicacion;
}

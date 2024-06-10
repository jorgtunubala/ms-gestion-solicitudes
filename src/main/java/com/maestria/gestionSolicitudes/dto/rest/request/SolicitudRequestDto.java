package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class SolicitudRequestDto {
    private Integer idTipoSolicitud;
    private Integer idEstudiante;
    private Integer idTutor;
    private DatosSolicitudHomologacionDto datosHomologacion;
    private List<InfoAdicionAsignaturaRequest> datosAdicionAsignatura;
    private CancelarAsignaturaRequest datosCancelarAsignatura;
    private AplazarSemestreRequest datosAplazarSemestre;
    private DatosSolicitudCursarAsignaturaDto datosCursarAsignatura;
    private AvalPasantiaInvRequest datosAvalPasantiaInv;
    private ApoyoEconomicoRequest datosApoyoEconomico;
    private ReconocimientoCreditosRequest datosReconocimientoCreditos;
    private AvalSeminarioActRequest datosAvalSeminario;
    private ApoyoEconomicoCongresoRequest datosApoyoEconomicoCongreso;
    private ApoyoEconomicoPublicacionEventoRequest datosApoyoEconomicoPublicacion;
    private List<DatosActividadDocenteRequest> datosActividadDocenteRequest;
    private Boolean requiereFirmaDirector;
    private String firmaEstudiante;
}

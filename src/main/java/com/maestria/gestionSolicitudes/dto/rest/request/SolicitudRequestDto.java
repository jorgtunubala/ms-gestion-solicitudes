package com.maestria.gestionSolicitudes.dto.rest.request;

import java.math.BigDecimal;
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
    private List<AvalComiteRequest> datosAvalComite;
    private SolicitudBecaRequest datosSolicitudBeca;
    private Boolean requiereFirmaDirector;
    private Integer idDirector;
    private Boolean firmaEstudiante;
    private String oficioPdf;
    private Integer numPaginaTutor;
    private Integer numPaginaDirector;
    private BigDecimal posXTutor;
    private BigDecimal posYTutor;
    private BigDecimal posXDirector;
    private BigDecimal posYDirector;
}

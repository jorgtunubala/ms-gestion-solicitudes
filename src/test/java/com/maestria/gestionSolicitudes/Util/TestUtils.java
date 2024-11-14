package com.maestria.gestionSolicitudes.Util;

import java.math.BigDecimal;

import com.maestria.gestionSolicitudes.domain.FirmaSolicitud;
import com.maestria.gestionSolicitudes.domain.HistorialEstadoSolicitudes;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.request.SolicitudRequestDto;

public class TestUtils {

    public static TiposSolicitud crearTiposSolicitudMock(Integer id, String codigo, String nombre) {
        TiposSolicitud tipoSolicitudMock = new TiposSolicitud();
        tipoSolicitudMock.setId(id);
        tipoSolicitudMock.setCodigo(codigo);
        tipoSolicitudMock.setNombre(nombre);
        tipoSolicitudMock.setEstado("ACTIVO");
        return tipoSolicitudMock;
    }

    public static Solicitudes crearSolicitudMock(TiposSolicitud tipoSolicitud) {
        Solicitudes solicitudMock = new Solicitudes();
        solicitudMock.setId(1);
        solicitudMock.setEstado("Radicada");
        solicitudMock.setTipoSolicitud(tipoSolicitud);
        solicitudMock.setIdEstudiante(1);
        solicitudMock.setIdTutor(2);
        solicitudMock.setRequiereFirmaDirector(false);
        return solicitudMock;
    }
    
    public static FirmaSolicitud crearFirmaSolicitudMock(Solicitudes solicitud) {
        FirmaSolicitud firmaSolicitudMock = new FirmaSolicitud();
        firmaSolicitudMock.setId(1);
        firmaSolicitudMock.setFirmaEstudiante(true);
        firmaSolicitudMock.setPosXTutor(BigDecimal.ONE);
        firmaSolicitudMock.setPosYTutor(BigDecimal.ONE);
        firmaSolicitudMock.setNumPaginaDirector(1);
        firmaSolicitudMock.setPosXDirector(BigDecimal.ONE);
        firmaSolicitudMock.setPosYDirector(BigDecimal.ONE);
        firmaSolicitudMock.setFirmaDirector(false);
        firmaSolicitudMock.setFirmaTutor(true);
        firmaSolicitudMock.setSolicitud(solicitud);
        return firmaSolicitudMock;
    }

    public static SolicitudRequestDto crearSolicitudRequestDto(Integer idTipoSolicitud) {
        SolicitudRequestDto solicitudDto = new SolicitudRequestDto();
        solicitudDto.setIdTipoSolicitud(idTipoSolicitud);
        solicitudDto.setIdEstudiante(1);
        solicitudDto.setIdTutor(2);
        solicitudDto.setDatosHomologacion(null);
        solicitudDto.setDatosAdicionAsignatura(null);
        solicitudDto.setDatosCancelarAsignatura(null);
        solicitudDto.setDatosAplazarSemestre(null);
        solicitudDto.setDatosCursarAsignatura(null);
        solicitudDto.setDatosAvalPasantiaInv(null);
        solicitudDto.setDatosApoyoEconomico(null);
        solicitudDto.setDatosReconocimientoCreditos(null);
        solicitudDto.setDatosAvalSeminario(null);
        solicitudDto.setDatosApoyoEconomicoCongreso(null);
        solicitudDto.setDatosApoyoEconomicoPublicacion(null);
        solicitudDto.setDatosActividadDocenteRequest(null);
        solicitudDto.setDatosAvalComite(null);
        solicitudDto.setDatosSolicitudBeca(null);
        solicitudDto.setRequiereFirmaDirector(false);
        solicitudDto.setIdDirector(null);
        solicitudDto.setFirmaEstudiante(true);
        solicitudDto.setOficioPdf("oficioEnBase64");
        solicitudDto.setPosXDirector(BigDecimal.ONE);
        solicitudDto.setPosXTutor(BigDecimal.ONE);
        solicitudDto.setPosYDirector(BigDecimal.ONE);
        solicitudDto.setPosYTutor(BigDecimal.ONE);
        solicitudDto.setNumPaginaDirector(1);
        solicitudDto.setNumPaginaTutor(1);
        return solicitudDto;
    }

    public static HistorialEstadoSolicitudes crearHistorialEstadoSolicitudesMock(String estado) {
        HistorialEstadoSolicitudes historialEstadoSolicitudesMock = new HistorialEstadoSolicitudes();
        historialEstadoSolicitudesMock.setId(1);
        historialEstadoSolicitudesMock.setEstado(estado);
        return historialEstadoSolicitudesMock;
    }
}

package com.maestria.gestionSolicitudes.dto.rest.response;

import java.util.List;

import com.maestria.gestionSolicitudes.dto.rest.request.AprobarAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.AsignaturaOtroPrograma.AprobarAsignaturaOPRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.AvalComite.AprobarAvalComiteRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.homologaciones.AprobarHomologacionRequest;

import lombok.Data;

@Data
public class SolicitudEnComiteResponse {
    private Integer idSolicitud;
    private Boolean enComite;
    private String avaladoComite;
    private String conceptoComite;
    private String numeroActa;
    private String fechaAval;
    private List<AprobarAsignaturaRequest> asignaturasAprobadas;
    private List<AprobarHomologacionRequest> asignaturasHomologadas;
    private List<AprobarAsignaturaOPRequest> asignaturasOtroPrograma;
    private List<AprobarAvalComiteRequest> avalActPracticaDocente;
    private List<AprobarAvalComiteRequest> reconocimientoCreditosPD;
}

package com.maestria.gestionSolicitudes.dto.rest.request;

import java.util.Map;

import lombok.Data;

@Data
public class DatosEnvioCorreo {
    String tipoSolicitud;
    String radicado;
    Map<String, String> documentos;
    String dirigidoA;
    String nombreEstudiante;
    String correoEstudiante;
    String nombreTutor;
    String correoTutor;
    String nombreDirector;
    String correoDirector;
    String nombreCoordinador;
    String correoCoordiandor;

    public DatosEnvioCorreo(){
        correoEstudiante = "juancamilocorreacuellar@gmail.com";
        correoTutor = "juancamilocorreacuellar@gmail.com";
        correoDirector = "juancamilocorreacuellar@gmail.com";
        correoCoordiandor = "juancamilocorreacuellar@gmail.com";
        nombreCoordinador = "Hugo Ordoñez";
    }
}

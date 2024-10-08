package com.maestria.gestionSolicitudes.service.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.domain.AdicionarAsignatura;
import com.maestria.gestionSolicitudes.domain.AsignaturaAdicionada;
import com.maestria.gestionSolicitudes.domain.AsignaturaCancelada;
import com.maestria.gestionSolicitudes.domain.CancelarAsignatura;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.dto.client.DocentesAsignaturasResponse;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.CancelarAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.InfoAdicionAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.response.InfoAdicionAsignaturaResponse;
import com.maestria.gestionSolicitudes.repository.AdicionarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaAdicionadaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaCanceladaRepository;
import com.maestria.gestionSolicitudes.repository.CancelarAsignaturaRepository;
import com.maestria.gestionSolicitudes.service.client.GestionAsignaturasService;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
import com.maestria.gestionSolicitudes.service.rest.AdicionAsignaturaService;

@Service
public class AdicionAsignaturaServiceImpl implements AdicionAsignaturaService {

    @Autowired
    private GestionAsignaturasService gestionAsignaturasService;
    @Autowired
    private GestionDocentesEstudiantesService gestionDocentesEstudiantesService;
    @Autowired
    private AdicionarAsignaturaRepository adicionarAsignaturaRepository;
    @Autowired
    private AsignaturaAdicionadaRepository asignaturaAdicionadaRepository;
    @Autowired
    private CancelarAsignaturaRepository cancelarAsignaturaRepository;
    @Autowired
    private AsignaturaCanceladaRepository asignaturaCanceladaRepository;

	@Override
	public List<InfoAdicionAsignaturaResponse> listarDocentesAsignaturas() {
		List<DocentesAsignaturasResponse> listaDocentesAsignaturasResponse = gestionAsignaturasService
                            .listarDocentesAsignaturas();
        List<InfoAdicionAsignaturaResponse> lisaInfoAsignaturas = new ArrayList<>();
        for (DocentesAsignaturasResponse docentesAsignaturasResponse : listaDocentesAsignaturasResponse) {
            InfoAdicionAsignaturaResponse info = new InfoAdicionAsignaturaResponse();
            info.setId(docentesAsignaturasResponse.getId());
            info.setNombreAsignatura(docentesAsignaturasResponse.getNombreAsignatura());
            info.setCodigoAsignatura(docentesAsignaturasResponse.getCodigoAsignatura());            
            InformacionPersonalDto infoDocente = gestionDocentesEstudiantesService
                    .obtenerTutor(docentesAsignaturasResponse.getIdDocente().toString());
            info.setNombreDocente(infoDocente.obtenerNombreCompleto());
            String infoAsignatura = docentesAsignaturasResponse.getNombreAsignatura();
            infoAsignatura+= " " + docentesAsignaturasResponse.getCodigoAsignatura();
            infoAsignatura+= " - " + infoDocente.obtenerNombreCompleto();
            info.setInfoAsignatura(infoAsignatura);
            lisaInfoAsignaturas.add(info);
        }
        return lisaInfoAsignaturas;
	}

	@Override
    @Transactional
	public Boolean registrarAdicionAsignaturas(Solicitudes solicitud, List<InfoAdicionAsignaturaRequest> listaAsignaturas) throws Exception {		
        Boolean registro = false;
        try {
            AdicionarAsignatura adicionarAsignatura = new AdicionarAsignatura();
            adicionarAsignatura.setSolicitud(solicitud);
            adicionarAsignatura = adicionarAsignaturaRepository.save(adicionarAsignatura);
            List<AsignaturaAdicionada> asignaturaAdicionadas = new ArrayList<>();
            for (InfoAdicionAsignaturaRequest info : listaAsignaturas) {
                AsignaturaAdicionada asignaturaAdicionada = new AsignaturaAdicionada();
                asignaturaAdicionada.setAdicionarAsignatura(adicionarAsignatura);
                asignaturaAdicionada.setIdAsignatura(null);
                asignaturaAdicionada.setNombreAsignatura(info.getNombreAsignatura());
                asignaturaAdicionada.setIdDocente(info.getIdDocente());
                asignaturaAdicionada.setEstado("Pendiente Aval");
                asignaturaAdicionada.setGrupo(info.getGrupo());
                asignaturaAdicionadas.add(asignaturaAdicionada);
            }
            asignaturaAdicionadaRepository.saveAll(asignaturaAdicionadas);
            registro = true;
        } catch (Exception e) {            
            throw e;
        }        
        return registro;
    }

    @Override
    public Boolean registrarCancelarAsignaturas(Solicitudes solicitud, CancelarAsignaturaRequest datosCancelarAsignatura) throws Exception {
        Boolean registro = false;
        try {
            CancelarAsignatura cancelarAsignatura = new CancelarAsignatura();
            cancelarAsignatura.setSolicitud(solicitud);
            cancelarAsignatura.setMotivo(datosCancelarAsignatura.getMotivo());
            cancelarAsignatura = cancelarAsignaturaRepository.save(cancelarAsignatura);
            List<AsignaturaCancelada> asignaturasCanceladas = new ArrayList<>();
            for (InfoAdicionAsignaturaRequest info : datosCancelarAsignatura.getListaAsignaturas()) {
                AsignaturaCancelada asignaturaCancelada = new AsignaturaCancelada();
                asignaturaCancelada.setCancelarAsignatura(cancelarAsignatura);
                asignaturaCancelada.setIdAsignatura(null);
                asignaturaCancelada.setNombreAsignatura(info.getNombreAsignatura());
                asignaturaCancelada.setIdDocente(info.getIdDocente());
                asignaturaCancelada.setEstado("Pendiente Aval");
                asignaturaCancelada.setGrupo(info.getGrupo());
                asignaturasCanceladas.add(asignaturaCancelada);
            }
            asignaturaCanceladaRepository.saveAll(asignaturasCanceladas);
            registro = true;
        } catch (Exception e) {            
            throw e;
        }        
        return registro;
    }
    
}

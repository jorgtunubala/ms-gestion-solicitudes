package com.maestria.gestionSolicitudes.service.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.domain.AdicionarAsignatura;
import com.maestria.gestionSolicitudes.domain.AsignaturaAdicionada;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.dto.client.DocentesAsignaturasResponse;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.response.InfoAdicionAsignaturaResponse;
import com.maestria.gestionSolicitudes.repository.AdicionarAsignaturaRepository;
import com.maestria.gestionSolicitudes.repository.AsignaturaAdicionadaRepository;
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
	public Boolean registrarAdicionAsignaturas(Solicitudes solicitud, List<Integer> listaAsignaturas) throws Exception {		
        Boolean registro = false;
        try {
            AdicionarAsignatura adicionarAsignatura = new AdicionarAsignatura();
            adicionarAsignatura.setSolicitud(solicitud);
            adicionarAsignatura = adicionarAsignaturaRepository.save(adicionarAsignatura);
            List<AsignaturaAdicionada> asignaturaAdicionadas = new ArrayList<>();
            for (Integer idAsignatura : listaAsignaturas) {
                AsignaturaAdicionada asignaturaAdicionada = new AsignaturaAdicionada();
                asignaturaAdicionada.setAdicionarAsignatura(adicionarAsignatura);
                asignaturaAdicionada.setIdAsignatura(idAsignatura);
                asignaturaAdicionadas.add(asignaturaAdicionada);
            }
            asignaturaAdicionadaRepository.saveAll(asignaturaAdicionadas);
            registro = true;
        } catch (Exception e) {            
            throw e;
        }        
        return registro;
    }
    
}

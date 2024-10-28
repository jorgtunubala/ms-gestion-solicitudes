package com.maestria.gestionSolicitudes.service.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maestria.gestionSolicitudes.domain.RolInformacion;
import com.maestria.gestionSolicitudes.dto.rest.response.Rolinformacion.RolInformacionResponse;
import com.maestria.gestionSolicitudes.repository.RolInformacionRepository;
import com.maestria.gestionSolicitudes.service.rest.RolInformacionService;

@Service
public class RolInformacionServiceImpl implements RolInformacionService {

    @Autowired
    private RolInformacionRepository rolInformacionRepository;


    @Override
    public RolInformacionResponse obtenerRolInformacion(String cargo) {
        List<RolInformacion> rolesInfo = rolInformacionRepository.findByCargoContaining(cargo);
        RolInformacionResponse rolInfo = new RolInformacionResponse();
        rolInfo.setCargo(rolesInfo.get(0).getCargo());
        rolInfo.setNombreCompleto(rolesInfo.get(0).getNombreCompleto());
        rolInfo.setTitulo(rolesInfo.get(0).getTitulo());
        rolInfo.setTratamiento(rolesInfo.get(0).getTratamiento());
        return rolInfo;
    }


    @Override
    public Boolean guardarRolInformacion(RolInformacionResponse rolInfo) {
        List<RolInformacion> rolesInfo = rolInformacionRepository.findByCargoContaining(rolInfo.getCargo());
        RolInformacion rolInformacion = rolesInfo.get(0);
        rolInformacion.setCargo(rolInfo.getCargo());
        rolInformacion.setNombreCompleto(rolInfo.getNombreCompleto());
        rolInformacion.setTitulo(rolInfo.getTitulo());
        rolInformacion.setTratamiento(rolInfo.getTratamiento());
        rolInformacionRepository.save(rolInformacion);
        return true;
    }
    
}

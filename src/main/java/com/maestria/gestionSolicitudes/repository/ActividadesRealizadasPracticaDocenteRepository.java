package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.ActividadesRealizadasPracticaDocente;
import com.maestria.gestionSolicitudes.domain.Solicitudes;

public interface ActividadesRealizadasPracticaDocenteRepository 
        extends JpaRepository<ActividadesRealizadasPracticaDocente, Integer>{
    
    List<ActividadesRealizadasPracticaDocente> findBySolicitud(Solicitudes solicitud);
}

package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.ActividadesRealizadasPracticaDocente;
import com.maestria.gestionSolicitudes.domain.EnlacesActividadesRealizadas;
import com.maestria.gestionSolicitudes.domain.SubTiposSolicitud;

public interface EnlacesActividadesRealizadasRepository 
        extends JpaRepository<EnlacesActividadesRealizadas, Integer> {
    
    // Método para encontrar registros por actividadRealizada y subTiposSolicitud
    List<EnlacesActividadesRealizadas> findByActividadRealizadaAndSubTiposSolicitud(
            ActividadesRealizadasPracticaDocente actividadRealizada, 
            SubTiposSolicitud subTiposSolicitud);
}

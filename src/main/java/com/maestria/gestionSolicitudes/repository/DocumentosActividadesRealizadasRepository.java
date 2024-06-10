package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.ActividadesRealizadasPracticaDocente;
import com.maestria.gestionSolicitudes.domain.DocumentosActividadesRealizadas;
import com.maestria.gestionSolicitudes.domain.SubTiposSolicitud;

public interface DocumentosActividadesRealizadasRepository 
        extends JpaRepository<DocumentosActividadesRealizadas, Integer> {
    
    // Método para encontrar registros por actividadRealizada y subTiposSolicitud
    List<DocumentosActividadesRealizadas> findByActividadRealizadaAndSubTiposSolicitud(
            ActividadesRealizadasPracticaDocente actividadRealizada, 
            SubTiposSolicitud subTiposSolicitud);
}

package com.maestria.gestionSolicitudes.repository;

import com.maestria.gestionSolicitudes.domain.Estudiante;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoEstudianteRepository extends JpaRepository<Estudiante, Integer>{
    @Query(value = """
        SELECT s.id, s.estado_maestria
        FROM estudiantes s
        ORDER BY s.fecha_modificacion desc
        """, nativeQuery = true)
        List<Estudiante> findAllEstadoEstudiantesOrderByFechaModificacion();
    
} 


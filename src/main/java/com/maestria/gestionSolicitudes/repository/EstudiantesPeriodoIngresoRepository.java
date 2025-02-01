package com.maestria.gestionSolicitudes.repository;

import com.maestria.gestionSolicitudes.domain.Estudiantes;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudiantesPeriodoIngresoRepository extends JpaRepository<Estudiantes, Integer>{
    @Query(value = """
        SELECT s.id, s.periodo_ingreso
        FROM estudiantes s
        ORDER BY s.fecha_modificacion desc
        """, nativeQuery = true)
        List<Estudiantes> findAllEstudiantesPeriodoIngresoOrderByFechaModificacion();
    
} 


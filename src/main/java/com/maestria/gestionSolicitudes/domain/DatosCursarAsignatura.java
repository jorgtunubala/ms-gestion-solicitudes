package com.maestria.gestionSolicitudes.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.maestria.gestionSolicitudes.comun.util.BooleanConverter;

import lombok.Data;

@Entity
@Table(name = "datos_cursar_asignatura")
@Data
public class DatosCursarAsignatura {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cursar_asignatura")
    private CursarAsignatura cursarAsignatura;

    @Column(name = "id_asignatura_externa")
    private Integer idAsignaturaExterna;

    @Column(name = "codigo_asignatura")
    private String codigoAsignatura;
    
    @Column(name = "grupo")
    private String grupo;
    
    @Column(name = "nombre_docente")
    private String nombreDocente;
    
    @Column(name = "titulo_docente")
    private String tituloDocente;
    
    @Column(name = "carta_aceptacion")
    private String cartaAceptacion;
    
    @Column(name = "estado")
    private String estado;

    @Column(name = "aprobado_comite")
    @Convert(converter = BooleanConverter.class)
    private Boolean aprobadoComite;

    @Column(name = "aprobado_concejo")
    @Convert(converter = BooleanConverter.class)
    private Boolean aprobadoConcejo;
}

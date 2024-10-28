package com.maestria.gestionSolicitudes.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.maestria.gestionSolicitudes.comun.util.BooleanConverter;

import lombok.*;

@Entity
@Table(name="asignaturas_homologadas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsignaturasHomologadas extends EntidadPrincipal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_homologacion")
    private Homologaciones homologacion;

    @Column(name = "id_asignatura_homologar")
    private Integer asignaturaHomologar;

    @Column(name = "id_asignatura_externa")
    private Integer asignaturaExterna;

    @Column(name = "calificacion_obtenida")
    private Double calificacionObtenida;

    @Column(name = "estado")
    private String estado;

    @Column(name = "aprobado_comite")
    @Convert(converter = BooleanConverter.class)
    private Boolean aprobadoComite;

    @Column(name = "aprobado_concejo")
    @Convert(converter = BooleanConverter.class)
    private Boolean aprobadoConcejo;
}

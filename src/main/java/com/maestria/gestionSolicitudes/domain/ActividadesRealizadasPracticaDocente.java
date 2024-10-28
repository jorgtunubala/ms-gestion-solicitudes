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
import javax.persistence.Table;

import com.maestria.gestionSolicitudes.comun.util.BooleanConverter;

import lombok.Data;

@Entity
@Table(name = "actividades_realizadas_practica_docente")
@Data
public class ActividadesRealizadasPracticaDocente {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sub_tipo_solicitud")
    private SubTiposSolicitud subTiposSolicitud;

    @Column(name = "intensidad_horaria")
    private Integer intensidadHoraria;

    @Column(name = "horas_reconocer")
    private BigDecimal horasReconocer;

    @Column(name = "creditos")
    private Integer creditos;

    @Column(name = "estado")
    private String estado;

    @Column(name = "aprobado_comite")
    @Convert(converter = BooleanConverter.class)
    private Boolean aprobadoComite;

    @Column(name = "aprobado_concejo")
    @Convert(converter = BooleanConverter.class)
    private Boolean aprobadoConcejo;
}

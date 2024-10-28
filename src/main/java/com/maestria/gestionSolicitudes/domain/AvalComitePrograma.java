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
@Table(name = "aval_comite_programa")
@Data
public class AvalComitePrograma {
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

    @Column(name = "descripcion_actividad")
    private String descripcionActividad;

    @Column(name = "documento_adjunto")
    private String documentoAdjunto;

    @Column(name = "estado")
    private String estado;

    @Column(name = "aprobado_comite")
    @Convert(converter = BooleanConverter.class)
    private Boolean aprobadoComite;

    @Column(name = "aprobado_concejo")
    @Convert(converter = BooleanConverter.class)
    private Boolean aprobadoConcejo;
}

package com.maestria.gestionSolicitudes.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.maestria.gestionSolicitudes.comun.util.BooleanConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="solicitudes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Solicitud extends EntidadPrincipal {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOLICITUDES_GENERATOR")
    @SequenceGenerator(sequenceName = "SEQ_ID_SOLICITUDES", name = "SOLICITUDES_GENERATOR", allocationSize = 1)
    private Integer id;

    @Column(name = "tiposolicitud")
    private String tipoSolicitud;

    @Column(name = "documentoasociado")
    private String documentoAsociado;

    @Column(name = "respuestasolicitud")
    private String respuestaSolicitud;

    @Column(name = "fechaactasolic")
    private LocalDate fechaActaSolic;

    @Column(name = "observacionsolic")
    private String observaciones;

    @Column(name = "estado")
    @Convert(converter = BooleanConverter.class)
    private String estado;

    @Column(name = "codigo")
    private String codigo;
}

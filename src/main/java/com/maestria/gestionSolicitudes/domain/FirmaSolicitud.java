package com.maestria.gestionSolicitudes.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="firmas_solicitud")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirmaSolicitud {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;

    @Column(name = "num_pagina_tutor")
    private Integer numPaginaTutor;

    @Column(name = "pos_x_tutor")
    private BigDecimal posXTutor;

    @Column(name = "pos_y_tutor")
    private BigDecimal posYTutor;

    @Column(name = "num_pagina_director")
    private Integer numPaginaDirector;

    @Column(name = "pos_x_director")
    private BigDecimal posXDirector;

    @Column(name = "pos_y_director")
    private BigDecimal posYDirector;

    @Column(name = "firma_estudiante")
    private Boolean firmaEstudiante;

    @Column(name = "firma_tutor")
    private Boolean firmaTutor;

    @Column(name = "firma_director")
    private Boolean firmaDirector;
    
}

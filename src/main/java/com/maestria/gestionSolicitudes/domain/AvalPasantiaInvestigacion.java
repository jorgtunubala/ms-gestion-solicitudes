package com.maestria.gestionSolicitudes.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "avales_pasantia_investigacion")
@Data
public class AvalPasantiaInvestigacion {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;

    @Column(name = "lugar_pasantia")
    private String lugarPasantia;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;  

    @Column(name = "fecha_fin")
    private Date fechaFin;  
}

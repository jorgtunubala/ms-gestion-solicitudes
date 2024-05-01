package com.maestria.gestionSolicitudes.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "apoyos_economicos_congresos")
@Data
public class ApoyoEconomicoCongreso {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;

    @Column(name = "nombre_congreso")
    private String nombreCongreso;

    @Column(name = "tipo_congreso")
    private String tipoCongreso;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;  

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "director_grupo_inv")
    private Integer idDirector;

    @Column(name = "titulo_publicacion")
    private String tituloPublicacion;

    @Column(name = "valor_apoyo")
    private BigDecimal valorApoyo;

    @Column(name = "entidad_bancaria")
    private String entidadBancaria;

    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @Column(name = "numero_cedula_asociada")
    private String numeroCedulaAsociada;

    @Column(name = "direccion_residencia")
    private String direccionResidencia;

}

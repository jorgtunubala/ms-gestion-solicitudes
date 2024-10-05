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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="sub_tipos_solicitudes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubTiposSolicitud extends EntidadPrincipal {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_solicitud")
    private TiposSolicitud tipoSolicitud;

    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "peso")
    private BigDecimal peso;

    @Column(name = "horas_asignadas")
    private Integer horasAsignadas;

    @Column(name = "estado")
    private String estado;

    @Column(name = "requiere_aval")
    @Convert(converter = BooleanConverter.class)
    private Boolean requiereAval;
}

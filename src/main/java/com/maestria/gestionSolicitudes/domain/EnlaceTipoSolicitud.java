package com.maestria.gestionSolicitudes.domain;

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

@Entity
@Table(name = "enlaces_tipos_solicitudes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnlaceTipoSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_solicitud")
    private TiposSolicitud tiposSolicitud;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "url_real")
    private String urlReal;

    @Column(name = "url_acortada")
    private String urlAcortada;
}

package com.maestria.gestionSolicitudes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="requisitos_solicitud")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequisitoSolicitud extends EntidadPrincipal {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REQUISITO_SOLICITUD_GENERATOR")
    @SequenceGenerator(sequenceName = "SEQ_ID_REQUISITO_SOLICITUD", name = "REQUISITO_SOLICITUD_GENERATOR", allocationSize = 1)
    private Integer id;

    @Column(name = "titulo_documento")
    private String tituloDocumento;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "articulo")
    private String articulo;

    @Column(name = "tener_en_cuenta")
    private String tenerEnCuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud")
    private Solicitud solicitud;

}

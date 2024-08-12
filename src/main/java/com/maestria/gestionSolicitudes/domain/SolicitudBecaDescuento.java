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
import lombok.Setter;

@Entity
@Table(name="solicitud_beca_descuento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudBecaDescuento {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;    

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "formato_solicitud")
    private String formatoSolicitudBeca;
}

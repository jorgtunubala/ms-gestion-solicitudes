package com.maestria.gestionSolicitudes.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="solicitudes_en_comite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudesEnComite {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;

    @Column(name = "avalado_comite")
    private String avaladoComite;

    @Column(name = "concepto_comite")
    private String conceptoComite;

    @Column(name = "numero_acta")
    private String numeroActa;

    @Column(name = "fecha_aval")
    private Date fechaAval;
    
}

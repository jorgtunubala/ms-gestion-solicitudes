package com.maestria.gestionSolicitudes.domain;

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
@Table(name="solicitudes_en_concejo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudesEnConcejo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;

    @Column(name = "resolucion")
    private String resolucion;
    
}

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

import lombok.*;

@Entity
@Table(name="homologaciones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Homologaciones extends EntidadPrincipal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitud solicitud;

    @Column(name = "id_tutor")
    private Integer idTutor;

    @Column(name = "estado")
    private String estado;
}

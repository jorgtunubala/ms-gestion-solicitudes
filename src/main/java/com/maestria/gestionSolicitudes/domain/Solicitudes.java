package com.maestria.gestionSolicitudes.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="solicitudes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Solicitudes extends EntidadPrincipal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_solicitud")
    private TiposSolicitud tipoSolicitud;

    @Column(name = "id_tutor")
    private Integer idTutor;

    @Column(name = "estado")
    private String estado;
}
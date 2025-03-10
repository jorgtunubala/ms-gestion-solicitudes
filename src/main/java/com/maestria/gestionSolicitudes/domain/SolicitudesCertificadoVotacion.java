package com.maestria.gestionSolicitudes.domain;

import java.sql.Date;
import javax.persistence.*;

import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudesCertificadoVotacion {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @Column(name = "estado")
    private String estado_solicitud;
    
    @Column(name = "id_tipo_solicitud")
    private Integer idTipoSolicitud;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void prePersist() {
        fechaModificacion = LocalDateTime.now();
    }
}
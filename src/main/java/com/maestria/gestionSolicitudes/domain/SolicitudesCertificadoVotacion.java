package com.maestria.gestionSolicitudes.domain;

import java.sql.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudesCertificadoVotacion{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_estudiante")
    private Integer idEstudiante;

    @Column(name = "estado")
    private String estado_solicitud;
    
    @Column(name = "id_tipo_solicitud")
    private String idTipoSolicitud;

    @Column(name = "fecha_creacion")
    private String fechaCreacion;

    @Column(name = "fecha_modificacion")
    private String fechaModificacion;
}
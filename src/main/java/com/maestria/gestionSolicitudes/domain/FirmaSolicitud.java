package com.maestria.gestionSolicitudes.domain;

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
import lombok.Setter;

@Entity
@Table(name="firmas_solicitud")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirmaSolicitud {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud")
    private Solicitudes solicitud;

    @Column(name = "firma_tutor")
    private String firmaTutor;

    @Column(name = "firma_estudiante")
    private String firmaEstudiante;

    @Column(name = "firma_director")
    private String firmaDirector;
}

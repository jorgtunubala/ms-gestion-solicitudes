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

import lombok.Data;

@Entity
@Table(name = "asignaturas_adicionadas")
@Data
public class AsignaturaAdicionada {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_adicionar_asignatura")
    private AdicionarAsignatura adicionarAsignatura;

    @Column(name = "id_asignatura")
    private Integer idAsignatura;

    @Column(name = "noombre_asignatura")
    private String nombreAsignatura;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "id_docente")
    private Integer idDocente;

    @Column(name = "estado")
    private String estado;
}

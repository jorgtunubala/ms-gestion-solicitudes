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
@Table(name="documentos_adjuntos_homologacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentosAdjuntosHomologacion {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_homologacion")
    private Homologaciones homologacion;
    
    @Column(name = "documento")
    private String documento;
}

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
@Table(name="documentos_concejo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentosConcejo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud_concejo")
    private SolicitudesEnConcejo solicitudConcejo;

    @Column(name = "documento")
    private String documento;
    
}

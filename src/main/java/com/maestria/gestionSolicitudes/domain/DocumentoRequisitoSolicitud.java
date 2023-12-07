package com.maestria.gestionSolicitudes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="documentos_requisitos_solicitud")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoRequisitoSolicitud extends EntidadPrincipal {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENTO_REQUISITO_SOLICITUD_GENERATOR")
    @SequenceGenerator(sequenceName = "SEQ_ID_DOCUMENTO_REQUISITO_SOLICITUD", name = "DOCUMENTO_REQUISITO_SOLICITUD_GENERATOR", allocationSize = 1)
    private Integer id;

    @Column(name = "nombre_documento")
    private String nombreDocumento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_requisito_solicitud")
    private RequisitoSolicitud requisitoSolicitud;
}

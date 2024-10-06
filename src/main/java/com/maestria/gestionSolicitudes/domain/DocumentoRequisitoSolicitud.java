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

@Entity
@Table(name="documentos_requisitos_solicitud")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoRequisitoSolicitud extends EntidadPrincipal {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_documento")
    private String nombreDocumento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_requisito_solicitud")
    private RequisitoSolicitud requisitoSolicitud;

    @Column(name = "adjuntar_documento")
    @Convert(converter = BooleanConverter.class)
    private Boolean adjuntarDocumento;

    @Column(name = "abreviatura_documento")
    private String abreviaturaDocumento;
}

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
@Table(name="notas_documentos_requerido")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotaDocumentoRequerido extends EntidadPrincipal {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTA_DOCUMENTO_REQUERIDO_GENERATOR")
    @SequenceGenerator(sequenceName = "SEQ_ID_NOTA_DOCUMENTO_REQUERIDO", name = "NOTA_DOCUMENTO_REQUERIDO_GENERATOR", allocationSize = 1)
    private Integer id;

    @Column(name = "nota")
    private String nota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_req_solicitud")
    private RequisitoSolicitud requisitoSolicitud;      
}

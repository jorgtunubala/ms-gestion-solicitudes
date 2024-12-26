package com.maestria.gestionSolicitudes.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentosCertificadoVotacion {  
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Lob
    @Column(name = "documento_firmado")
    private byte[] documentoPDF;
     
}

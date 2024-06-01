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

@Entity
@Table(name="enlaces_subtipos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnlacesSubtipos {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sub_tipo_solicitud")
    private SubTiposSolicitud subTiposSolicitud;

    @Column(name = "nombre_requisito")
    private String nombreRequisito;
    
}

package com.maestria.gestionSolicitudes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tipos_solicitudes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TiposSolicitud extends EntidadPrincipal {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "estado")
    private String estado;
}

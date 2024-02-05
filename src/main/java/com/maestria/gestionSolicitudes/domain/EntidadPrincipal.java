package com.maestria.gestionSolicitudes.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;
    
@MappedSuperclass
@Data
public class EntidadPrincipal implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Size(max = 30)
    @Column(name = "usuario_creacion", length = 30)
    private String usuarioCreacion;

    @LastModifiedBy
    @Size(max = 30)
    @Column(name = "usuario_modificacion", length = 30)
    private String usuarioModificacion;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void prePersist() {
        usuarioCreacion = "1";
        usuarioModificacion = "1";
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
    }
}

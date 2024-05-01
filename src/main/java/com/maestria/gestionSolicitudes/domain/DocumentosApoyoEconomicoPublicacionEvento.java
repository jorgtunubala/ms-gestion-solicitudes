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
@Table(name = "documentos_apoyo_econo_pago_pub_evento")
@Data
public class DocumentosApoyoEconomicoPublicacionEvento {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_apoyo_econo_pago_pub_evento")
    private ApoyoEconomicoPublicacionEvento apoyoEconomicoPublicacionEvento;

    @Column(name = "documento")
    private String documento;
}

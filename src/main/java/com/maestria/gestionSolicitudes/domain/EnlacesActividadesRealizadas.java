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
@Table(name = "enlaces_actividades_realizadas")
@Data
public class EnlacesActividadesRealizadas {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_actividad_realizada")
    private ActividadesRealizadasPracticaDocente actividadRealizada;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sub_tipo_solicitud")
    private SubTiposSolicitud subTiposSolicitud;

    @Column(name = "enlace")
    private String enlace;
}

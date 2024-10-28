package com.maestria.gestionSolicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maestria.gestionSolicitudes.domain.RolInformacion;

public interface RolInformacionRepository extends JpaRepository<RolInformacion, Integer> {
    List<RolInformacion> findByCargoContaining(String cargo);
}

package com.backend.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rest.entity.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Integer>{

}

package com.backend.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rest.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}

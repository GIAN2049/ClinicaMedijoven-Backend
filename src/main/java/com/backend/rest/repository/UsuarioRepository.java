package com.backend.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rest.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}

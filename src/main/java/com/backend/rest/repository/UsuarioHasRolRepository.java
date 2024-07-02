package com.backend.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.backend.rest.entity.UsuarioHasRol;
import com.backend.rest.entity.UsuarioHasRolPK;

public interface UsuarioHasRolRepository extends JpaRepository<UsuarioHasRol, UsuarioHasRolPK> {

	@Modifying
	@Transactional
	@Query("DELETE FROM UsuarioHasRol uhr WHERE uhr.usuario.id = :usuarioId")
	void deleteByUsuarioId(@Param("usuarioId") Integer usuarioId);
}

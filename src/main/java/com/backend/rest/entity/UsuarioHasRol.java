package com.backend.rest.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario_rol")
public class UsuarioHasRol {
	@EmbeddedId
	private UsuarioHasRolPK pk;

	@ManyToOne
	@JoinColumn(name = "id_usuario", insertable=false, updatable=false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_rol", insertable=false, updatable=false)
	private Rol rol;
}

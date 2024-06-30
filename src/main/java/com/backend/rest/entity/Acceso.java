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
@Table(name = "tb_acceso")
public class Acceso {

	@EmbeddedId
	private AccesoPk pk;
	
	@ManyToOne
	@JoinColumn(name = "id_menu", insertable = false, updatable = false)
	private Menu menu;
	
	@ManyToOne
	@JoinColumn(name = "id_rol", insertable = false, updatable = false)
	private Rol rol;
}

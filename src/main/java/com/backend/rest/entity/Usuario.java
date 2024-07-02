package com.backend.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer id;
	
	private String nombre;
	
	private String apellidos;
	
	private String dni;
	
	private String correo;
	
	@Column(name = "numero_telefono")
	private String telefono;
	
	@Column(name = "username")
	private String login;
	
	private String password;
	
	private String sexo;
	
	public String getNombreCompleto() {
		if (nombre != null && apellidos != null) {
			return nombre.concat(" ").concat(apellidos);	
		}else {
			return ""; 
		}
	}
}

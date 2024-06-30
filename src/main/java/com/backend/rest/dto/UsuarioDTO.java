package com.backend.rest.dto;

import lombok.Data;

@Data
public class UsuarioDTO {

	private Integer id;
	
	private String nombre;
	
	private String apellidos;
	
	private String dni;
	
	private String correo;
	
	private String telefono;
	
	private String username;
	
	private String password;
	
	private RolDTO rol;
	
	private String sexo;
}

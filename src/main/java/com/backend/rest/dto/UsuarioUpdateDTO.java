package com.backend.rest.dto;

import java.util.List;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {
private Integer id;
	
	private String nombre;
	
	private String apellidos;
	
	private String dni;
	
	private String correo;
	
	private String telefono;
	
	private String login;
	
	private String password;
	
	private String sexo;
}

package com.backend.rest.dto;

import lombok.Data;

@Data
public class MedicoDTO {
	private int id;
	private UsuarioDTO usuario;
	private EspecialidadDTO especialidad;
    private boolean disponible;
}
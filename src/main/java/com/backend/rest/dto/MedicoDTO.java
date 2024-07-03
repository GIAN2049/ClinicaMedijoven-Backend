package com.backend.rest.dto;

import lombok.Data;

@Data
public class MedicoDTO {
	private Integer id;
	private UsuarioDTO usuario;
	private EspecialidadDTO especialidad;
    private boolean disponible;
}
package com.backend.rest.dto;

import lombok.Data;

@Data
public class MedicoUpdateDTO {
	private int id;
	private UsuarioUpdateDTO usuario;
	private EspecialidadDTO especialidad;
    private boolean disponible;
}

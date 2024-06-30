package com.backend.rest.dto;

import lombok.Data;

@Data
public class PacienteDTO {
	private int id;
    private UsuarioDTO usuario;
    private String tipoSangre;
	private boolean disponible;
}

package com.backend.rest.dto;

import lombok.Data;

@Data
public class PacienteUpdateDTO {
	private int id;
    private UsuarioUpdateDTO usuario;
    private String tipoSangre;
	private boolean disponible;
}

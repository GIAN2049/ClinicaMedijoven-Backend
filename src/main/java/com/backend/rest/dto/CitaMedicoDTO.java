package com.backend.rest.dto;

import java.time.LocalTime;
import java.util.Date;
import lombok.Data;

@Data
public class CitaMedicoDTO {
    private Integer id;

	private Date fechaCita;

	private LocalTime hora;
	
    private int idPaciente;

    private int idMedico;
}

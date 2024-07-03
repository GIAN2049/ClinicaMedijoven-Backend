package com.backend.rest.dto;


import java.util.Date;

import lombok.Data;

@Data
public class HistoriaClinicaDTO {
	private int id;
	private PacienteDTO paciente;
	private Date fechaRegistro;
	private String diagnostico;
	private String tratamientos;
	private String resultadosPruebas;
}

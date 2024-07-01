package com.backend.rest.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class HistoriaClinicaDTO {
	private int id;
	private PacienteDTO paciente;
	private LocalTime hora;
	private String diagnostico, tratamientos, resutaldos;
}

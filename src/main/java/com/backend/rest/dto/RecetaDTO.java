package com.backend.rest.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RecetaDTO {
	private Integer codigo;
	private Integer medico;
	private Integer paciente;
	private Integer especialidad;
	private Integer categoria;
	private Integer medicamento;
	private LocalDate fecha;
	private String detalle;
}
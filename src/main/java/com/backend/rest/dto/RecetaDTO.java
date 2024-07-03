package com.backend.rest.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RecetaDTO {
	private Integer id;
	private MedicoDTO medico;
	private PacienteDTO paciente;
	private EspecialidadDTO especialidad;
	private CategoriaDTO categoria;
	private MedicamentoDTO medicamento;
	private Date fechaRegistro;
	private String detalles;
}
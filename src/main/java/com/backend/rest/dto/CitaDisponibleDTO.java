package com.backend.rest.dto;

import java.time.LocalTime;
import java.util.Date;


import lombok.Data;

@Data
public class CitaDisponibleDTO {

	private Integer id;
	
	private Date fechaCita;
	
	private LocalTime hora;
	
	private int idMedico;

	private boolean isReservado;
}

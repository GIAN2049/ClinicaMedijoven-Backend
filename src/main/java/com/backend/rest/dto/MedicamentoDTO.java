package com.backend.rest.dto;


import lombok.Data;

@Data
public class MedicamentoDTO {
	
	private Integer id;
	private String nombre;
	private int stock;
	private Double precio;
	private CategoriaDTO categoria;
}

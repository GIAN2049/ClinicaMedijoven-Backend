package com.backend.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_medico")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_medico")
	private Integer id;
	
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_especialidad", nullable = false)
	private Especialidad especialidad;
	
    private boolean disponible;
}

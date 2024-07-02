package com.backend.rest.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_menu")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String icon;
	private String link;
	
	@JsonIgnore
	@OneToMany(mappedBy = "menu")
    private List<MenuItem> items;
    
    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<Acceso> accesos;
}

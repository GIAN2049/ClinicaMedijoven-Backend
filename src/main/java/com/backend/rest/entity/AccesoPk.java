package com.backend.rest.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class AccesoPk implements Serializable{
	private int id_menu;
	private int id_rol;
}

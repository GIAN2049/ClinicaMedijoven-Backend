package com.backend.rest.dto;

import java.util.List;

import lombok.Data;

@Data
public class MenuDTO {
	private Integer id;
    private String nombre;
    private String icon;
    private String link;
    private List<MenuItemDTO> items;
}

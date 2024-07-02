package com.backend.rest.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.backend.rest.entity.Menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String login;
    private String nombreCompleto;
    private int idUsuario;
    private Collection<? extends GrantedAuthority> authorities;
    private List<Menu> menus;

    public JwtDto(String token,String login, String nombreCompleto, int idUsuario, Collection<? extends GrantedAuthority> authorities,List<Menu> lstMenus) {
        this.token = token;
        this.login = login;
        this.nombreCompleto = nombreCompleto;
        this.authorities = authorities;
        this.idUsuario = idUsuario;
        this.menus = lstMenus;
    }

    
}

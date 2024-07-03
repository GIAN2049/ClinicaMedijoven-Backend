package com.backend.rest.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.Menu;
import com.backend.rest.entity.Rol;
import com.backend.rest.entity.Usuario;
import com.backend.rest.repository.UsuarioRepository;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class UsuarioSeguridadServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		log.info(">>>loadUserByUsername >>> " + login);
		UserDetails userDetails = null;
		try {
			Usuario objUsuario = usuarioRepository.findByLogin(login);
			if (objUsuario != null) {
				log.info("==> Login =========== " + objUsuario);

				List<Rol> lstRol = usuarioRepository.traerRolesDeUsuario(objUsuario.getId());
				log.info("==> Roles =========== " + lstRol);
				
				List<Menu> lstmenus = usuarioRepository.findMenusByUserId(objUsuario.getId());
				log.info("==> Opciones =========== " + lstmenus);

				userDetails = UsuarioPrincipal.build(objUsuario, lstRol, lstmenus);
			}
		} catch (IndexOutOfBoundsException e) {
			throw new UsernameNotFoundException("Wrong username");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Database Error");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Unknown Error");
		}
		return userDetails;
	}

}

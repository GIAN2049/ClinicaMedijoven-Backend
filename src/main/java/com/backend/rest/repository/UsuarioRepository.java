package com.backend.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.rest.entity.Menu;
import com.backend.rest.entity.Rol;
import com.backend.rest.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	@Query("SELECT u FROM Usuario u where u.login = ?1 and u.password=?2")
	public Usuario findByLoginAndPassword(String login, String pass);
	
	@Query("SELECT u FROM Usuario u WHERE u.login = ?1")
	public abstract Usuario findByLogin(String login);
	
	@Query("Select r from Rol r, UsuarioHasRol u where r.id = u.rol.id and u.usuario.id = :var_idUsuario")
	public abstract List<Rol> traerRolesDeUsuario(@Param("var_idUsuario")int idUsuario);
	
	@Query("SELECT m FROM Acceso a JOIN a.menu m where a.rol.id = ?1")
	public List<Menu> getMenusUser(Integer idRol);
}

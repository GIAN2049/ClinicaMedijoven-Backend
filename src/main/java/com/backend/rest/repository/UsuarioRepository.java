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
	
	@Query("Select r from Rol r, UsuarioHasRol u where r.id = u.rol.id and u.usuario.id = ?1")
	public abstract List<Rol> traerRolesDeUsuario(int idUsuario);

	@Query("SELECT m FROM Menu m " +
		       "JOIN Acceso a ON m.id = a.pk.id_menu " +
		       "JOIN Rol r ON r.id = a.pk.id_rol " +
		       "JOIN UsuarioHasRol ur ON ur.pk.id_rol = r.id " +
		       "WHERE ur.pk.id_usuario = :userId")
	public abstract	List<Menu> findMenusByUserId(@Param("userId") int userId);
	
	/*
	@Query("SELECT u FROM Usuario u"
			+ " JOIN UsuarioHasRol ur ON u.id = ur.pk.id_usuario"
			+ " JOIN Rol r ON r.id = ur.pk.id_rol")
	public abstract List<Usuario> listarUsuarioRol();
	 */
	
	@Query("SELECT DISTINCT u FROM Usuario u"
	        + " LEFT JOIN FETCH u.usuarioHasRoles ur"
	        + " LEFT JOIN FETCH ur.rol")
	public abstract List<Usuario> listarUsuarioRol();
}

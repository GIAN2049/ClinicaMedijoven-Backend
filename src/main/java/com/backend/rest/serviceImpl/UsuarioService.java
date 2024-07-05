package com.backend.rest.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.RolDTO;
import com.backend.rest.dto.UsuarioDTO;
import com.backend.rest.dto.UsuarioUpdateDTO;
import com.backend.rest.entity.Rol;
import com.backend.rest.entity.Usuario;
import com.backend.rest.entity.UsuarioHasRol;
import com.backend.rest.entity.UsuarioHasRolPK;
import com.backend.rest.repository.RolRepository;
import com.backend.rest.repository.UsuarioHasRolRepository;
import com.backend.rest.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService extends ICRUDImpl<Usuario, Integer> {

	@Autowired
	private UsuarioRepository repository;
	
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
	private UsuarioHasRolRepository usuarioHasRolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
	@Autowired
    private ModelMapper mapper;
	
	@Override
	public JpaRepository<Usuario, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	

	public List<Usuario> listarUsuarioRol() {
		// TODO Auto-generated method stub
		return repository.listarUsuarioRol();
	}
	
	public List<Rol> traerRolesPorIdUsuario(int idUsuario) {
		// TODO Auto-generated method stub
		return repository.traerRolesDeUsuario(idUsuario);
	}
	
	@Transactional
	public UsuarioDTO registrar(UsuarioDTO bean) {
		// Guardar Usuario
		Usuario usuario = mapper.map(bean, Usuario.class);
		usuario.setPassword(passwordEncoder.encode(bean.getPassword()));
		usuario = repository.save(usuario);

		
		
		// Guardar Roles
		for (RolDTO rolDTO : bean.getRoles()) {
			Rol rol = rolRepository.findById(rolDTO.getId()).orElseThrow(() -> new RuntimeException("Role not found"));
			UsuarioHasRol usuarioHasRol = new UsuarioHasRol();
			UsuarioHasRolPK pk = new UsuarioHasRolPK();
			pk.setId_usuario(usuario.getId());
			pk.setId_rol(rol.getId());
			usuarioHasRol.setPk(pk);
			usuarioHasRol.setUsuario(usuario);
			usuarioHasRol.setRol(rol);
			usuarioHasRolRepository.save(usuarioHasRol);
		}
		
		 // Asignar rol de ADMINISTRADOR por defecto
	    Rol rolAdmin = rolRepository.findById(1).orElseThrow(() -> new RuntimeException("Role ADMINISTRADOR not found"));
	    UsuarioHasRol usuarioHasRolAdmin = new UsuarioHasRol();
	    UsuarioHasRolPK pkAdmin = new UsuarioHasRolPK();
	    pkAdmin.setId_usuario(usuario.getId());
	    pkAdmin.setId_rol(rolAdmin.getId());
	    usuarioHasRolAdmin.setPk(pkAdmin);
	    usuarioHasRolAdmin.setUsuario(usuario);
	    usuarioHasRolAdmin.setRol(rolAdmin);
	    usuarioHasRolRepository.save(usuarioHasRolAdmin);
	    
		return mapper.map(usuario, UsuarioDTO.class);
	}
	
	@Transactional
	public UsuarioUpdateDTO actualizarUsuario(UsuarioUpdateDTO bean) {
		// Guardar Usuario
		Usuario usuario = repository.findById(bean.getId()).orElseThrow(() -> new RuntimeException("User not found"));
		mapper.map(bean, usuario);
		if (bean.getPassword() != null && !bean.getPassword().isEmpty()) {
			usuario.setPassword(passwordEncoder.encode(bean.getPassword()));
		}
		usuario = repository.save(usuario);

		// Guardar Roles
		
		for (RolDTO rolDTO : bean.getRoles()) {
			Rol rol = rolRepository.findById(rolDTO.getId()).orElseThrow(() -> new RuntimeException("Role not found"));
			UsuarioHasRol usuarioHasRol = new UsuarioHasRol();
			UsuarioHasRolPK pk = new UsuarioHasRolPK();
			pk.setId_usuario(usuario.getId());
			pk.setId_rol(rol.getId());
			usuarioHasRol.setPk(pk);
			usuarioHasRol.setUsuario(usuario);
			usuarioHasRol.setRol(rol);
			usuarioHasRolRepository.save(usuarioHasRol);
		}
		
		return mapper.map(usuario, UsuarioUpdateDTO.class);
	}
	
	public Optional<UsuarioDTO> getUsuarioId(int idUsuario) {
        return repository.findById(idUsuario)
                .map(m -> mapper.map(m, UsuarioDTO.class));
    }
}

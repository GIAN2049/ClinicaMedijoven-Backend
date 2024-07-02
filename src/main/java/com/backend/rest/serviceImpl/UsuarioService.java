package com.backend.rest.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.CategoriaDTO;
import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.dto.PacienteDTO;
import com.backend.rest.dto.RolDTO;
import com.backend.rest.dto.UsuarioDTO;
import com.backend.rest.entity.Categoria;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Paciente;
import com.backend.rest.entity.Rol;
import com.backend.rest.entity.Usuario;
import com.backend.rest.entity.UsuarioHasRol;
import com.backend.rest.entity.UsuarioHasRolPK;
import com.backend.rest.repository.MedicoRepository;
import com.backend.rest.repository.PacienteRepository;
import com.backend.rest.repository.RolRepository;
import com.backend.rest.repository.UsuarioHasRolRepository;
import com.backend.rest.repository.UsuarioRepository;
import com.backend.rest.utils.ModeloNotFoundException;

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
			usuarioHasRol.setUsuarioHasRolPk(pk);
			usuarioHasRol.setUsuario(usuario);
			usuarioHasRol.setRol(rol);
			usuarioHasRolRepository.save(usuarioHasRol);
		}

		return mapper.map(usuario, UsuarioDTO.class);
	}
	
	@Transactional
	public UsuarioDTO actualizar(UsuarioDTO bean) {
		// Guardar Usuario
		Usuario usuario = repository.findById(bean.getId()).orElseThrow(() -> new RuntimeException("User not found"));
		mapper.map(bean, usuario);
		if (bean.getPassword() != null && !bean.getPassword().isEmpty()) {
			usuario.setPassword(passwordEncoder.encode(bean.getPassword()));
		}
		usuario = repository.save(usuario);

		// Guardar Roles
		usuarioHasRolRepository.deleteByUsuarioId(usuario.getId());
		for (RolDTO rolDTO : bean.getRoles()) {
			Rol rol = rolRepository.findById(rolDTO.getId()).orElseThrow(() -> new RuntimeException("Role not found"));
			UsuarioHasRol usuarioHasRol = new UsuarioHasRol();
			UsuarioHasRolPK pk = new UsuarioHasRolPK();
			pk.setId_usuario(usuario.getId());
			pk.setId_rol(rol.getId());
			usuarioHasRol.setUsuarioHasRolPk(pk);
			usuarioHasRol.setUsuario(usuario);
			usuarioHasRol.setRol(rol);
			usuarioHasRolRepository.save(usuarioHasRol);
		}

		return mapper.map(usuario, UsuarioDTO.class);
	}
}

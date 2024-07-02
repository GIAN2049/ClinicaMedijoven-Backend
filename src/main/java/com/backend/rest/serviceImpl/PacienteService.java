package com.backend.rest.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.dto.PacienteDTO;
import com.backend.rest.dto.RolDTO;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Paciente;
import com.backend.rest.entity.Rol;
import com.backend.rest.entity.Usuario;
import com.backend.rest.entity.UsuarioHasRol;
import com.backend.rest.entity.UsuarioHasRolPK;
import com.backend.rest.repository.PacienteRepository;
import com.backend.rest.repository.RolRepository;
import com.backend.rest.repository.UsuarioHasRolRepository;
import com.backend.rest.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PacienteService extends ICRUDImpl<Paciente, Integer>{

	@Autowired
	private PacienteRepository repository;
	
	@Autowired
	private UsuarioHasRolRepository usuarioHasRolRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public JpaRepository<Paciente, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	public Optional<PacienteDTO> getPacienteId(int idUsuario, int idPaciente) {
		return repository.getPaciente(idUsuario, idPaciente)
				.map(m -> mapper.map(m, PacienteDTO.class));
	}
	
	public PacienteDTO getPacienteById(Integer idUser, Integer idPac) {
        Paciente paciente = repository.getPaciente(idUser, idPac)
                                        .orElseThrow(() -> new RuntimeException("Valor no presente"));
        
        PacienteDTO pacienteDto = mapper.map(paciente, PacienteDTO.class);
        return pacienteDto;
    }
	
	@Transactional
	public PacienteDTO registrarPaciente(PacienteDTO bean) {
		// Guardar usuario
		Usuario usuario = mapper.map(bean.getUsuario(), Usuario.class);
		usuario.setPassword(passwordEncoder.encode(bean.getUsuario().getPassword()));
		usuario = usuarioRepository.save(usuario);

		// Guardar paciente
		Paciente paciente = mapper.map(bean, Paciente.class);
		paciente.setUsuario(usuario);
		paciente = repository.save(paciente);

		// Guardar Roles
		for (RolDTO rolDTO : bean.getUsuario().getRoles()) {
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

		return mapper.map(paciente, PacienteDTO.class);
	}
	
	@Transactional
	public PacienteDTO actualizarPaciente(PacienteDTO bean) {
		// Guardar Usuario
		Usuario usuario = usuarioRepository.findById(bean.getUsuario().getId()).orElseThrow(() -> new RuntimeException("User not found"));
		mapper.map(bean.getUsuario(), usuario);
		if (bean.getUsuario().getPassword() != null && !bean.getUsuario().getPassword().isEmpty()) {
			usuario.setPassword(passwordEncoder.encode(bean.getUsuario().getPassword()));
		}
		usuario = usuarioRepository.save(usuario);

		// Guardar Paciente
		Paciente paciente = repository.findById(bean.getId()).orElseThrow(() -> new RuntimeException("Patient not found"));
		mapper.map(bean, paciente);
		paciente.setUsuario(usuario);
		paciente = repository.save(paciente);

		// Guardar Roles
		usuarioHasRolRepository.deleteByUsuarioId(usuario.getId());
		for (RolDTO rolDTO : bean.getUsuario().getRoles()) {
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

		return mapper.map(paciente, PacienteDTO.class);
	}

}

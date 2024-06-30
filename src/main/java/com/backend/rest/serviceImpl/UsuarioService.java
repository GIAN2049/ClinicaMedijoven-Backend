package com.backend.rest.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.CategoriaDTO;
import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.dto.PacienteDTO;
import com.backend.rest.dto.UsuarioDTO;
import com.backend.rest.entity.Categoria;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Paciente;
import com.backend.rest.entity.Rol;
import com.backend.rest.entity.Usuario;
import com.backend.rest.repository.MedicoRepository;
import com.backend.rest.repository.PacienteRepository;
import com.backend.rest.repository.RolRepository;
import com.backend.rest.repository.UsuarioRepository;
import com.backend.rest.utils.ModeloNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService extends ICRUDImpl<Usuario, Integer> {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
    private PacienteRepository pacienteRepository;
	
	@Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private RolRepository rolRepository;
	

	@Autowired
    private ModelMapper mapper;
	
	@Override
	public JpaRepository<Usuario, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	@Transactional
	public UsuarioDTO registrar(UsuarioDTO bean) {
		Usuario usuario = mapper.map(bean, Usuario.class);
		usuario = repository.save(usuario);
		//asignarRol(usuario, 1);
		UsuarioDTO usuarioDto = mapper.map(usuario, UsuarioDTO.class);
		
		return usuarioDto;
	}
	
	@Transactional
	public MedicoDTO registrarMedico(MedicoDTO bean) {
		Usuario usuario = mapper.map(bean.getUsuario(), Usuario.class);
		usuario = repository.save(usuario);

		Medico medico = mapper.map(bean, Medico.class);
		Medico medicoObj = medicoRepository.save(medico);
		MedicoDTO medicoDto = mapper.map(medicoObj, MedicoDTO.class);
		//asignarRol(usuario, 2);
		
		return medicoDto;
	}
	
	@Transactional
	public PacienteDTO registrarPaciente(PacienteDTO bean) {
		Usuario usuario = mapper.map(bean.getUsuario(), Usuario.class);
		usuario = repository.save(usuario);

		Paciente paciente = mapper.map(bean, Paciente.class);
		Paciente pacienteObj = pacienteRepository.save(paciente);
		PacienteDTO pacienteDto = mapper.map(pacienteObj, PacienteDTO.class);
		//asignarRol(usuario, 2);
		
		return pacienteDto;
	}


	/*
	private void asignarRol(Usuario usuario, int rolId) {
		Rol rol = rolRepository.findById(rolId)
				.orElseThrow(() -> new RuntimeException("El id del rol no existe: " + rolId));
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setUsuario(usuario);
		usuarioRol.setRol(rol);
		
		usuarioRolRepository.save(usuarioRol);
	}
	*/
}

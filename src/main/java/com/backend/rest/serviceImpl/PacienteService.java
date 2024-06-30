package com.backend.rest.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.dto.PacienteDTO;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Paciente;
import com.backend.rest.entity.Usuario;
import com.backend.rest.repository.PacienteRepository;
import com.backend.rest.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PacienteService extends ICRUDImpl<Paciente, Integer>{

	@Autowired
	private PacienteRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
	public PacienteDTO addUsuerWithPaciente(PacienteDTO bean) {
		Usuario usuario = mapper.map(bean.getUsuario(), Usuario.class);
		usuario = usuarioRepository.save(usuario);
		
		Paciente paciente = mapper.map(bean, Paciente.class);
		paciente.setUsuario(usuario);
		repository.save(paciente);
		PacienteDTO pacienteDto = mapper.map(paciente, PacienteDTO.class);
		
		return pacienteDto;
	}
	
	@Transactional
	public PacienteDTO updateUsuerWithPaciente(PacienteDTO bean) {
		Usuario usuario = usuarioRepository.findById(bean.getUsuario().getId())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		mapper.map(bean.getUsuario(), Usuario.class);
		
		usuarioRepository.save(usuario);
		
		Paciente paciente = repository.getPaciente(bean.getUsuario().getId(), bean.getId())
				.orElseThrow(() -> new RuntimeException("Doctor no encontrado"));;
		
		mapper.map(bean, paciente);
		paciente.setUsuario(usuario);
		paciente = repository.save(paciente);
		
		PacienteDTO pacienteDto = mapper.map(paciente, PacienteDTO.class);
				
		return pacienteDto;
	}

}

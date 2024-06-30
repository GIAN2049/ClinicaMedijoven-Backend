package com.backend.rest.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Usuario;
import com.backend.rest.repository.MedicoRepository;
import com.backend.rest.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicoService extends ICRUDImpl<Medico, Integer>{

	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public JpaRepository<Medico, Integer> getRepository() {
		// TODO Auto-generated method stub
		return medicoRepository;
	}
	
	public Optional<MedicoDTO> getMedicoId(int idUsuario, int idMedico) {
		return medicoRepository.getMedico(idUsuario, idMedico)
                .map(m -> mapper.map(m, MedicoDTO.class));
	}
	
	public MedicoDTO getMedicoById(Integer idUser, Integer idDoc) {
        Medico medico = medicoRepository.getMedico(idUser, idDoc)
                                        .orElseThrow(() -> new RuntimeException("Valor no presente"));
        
        MedicoDTO medicoDto = mapper.map(medico, MedicoDTO.class);
        return medicoDto;
    }
	
	@Transactional
	public MedicoDTO addUsuerWithMedico(MedicoDTO bean) {
		Usuario usuario = mapper.map(bean.getUsuario(), Usuario.class);
		usuario = usuarioRepository.save(usuario);
		
		Medico medico = mapper.map(bean, Medico.class);
		medico.setUsuario(usuario);
		medicoRepository.save(medico);
		MedicoDTO medicoDto = mapper.map(medico, MedicoDTO.class);
		
		return medicoDto;
	}
	
	@Transactional
	public MedicoDTO updateUsuerWithMedico(MedicoDTO bean) {
		Usuario usuario = usuarioRepository.findById(bean.getUsuario().getId())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		mapper.map(bean.getUsuario(), Usuario.class);
		
		usuarioRepository.save(usuario);
		
		Medico medico = medicoRepository.getMedico(bean.getUsuario().getId(), bean.getId())
				.orElseThrow(() -> new RuntimeException("Doctor no encontrado"));;
		
		mapper.map(bean, medico);
		medico.setUsuario(usuario);
		medico = medicoRepository.save(medico);
		
		MedicoDTO medicoDto = mapper.map(medico, MedicoDTO.class);
				
		return medicoDto;
	}
}

package com.backend.rest.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.CitaMedicoDTO;
import com.backend.rest.entity.CitaMedico;
import com.backend.rest.repository.CitaMedicoRepository;

@Service
public class CitaMedicoService extends ICRUDImpl<CitaMedico, Integer>{

	@Autowired
	private CitaMedicoRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public JpaRepository<CitaMedico, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}

	
	public List<CitaMedicoDTO> obtenerTodasLasCitasMedicos() {
        List<CitaMedico> citasMedicos = repository.findAll();
        return citasMedicos.stream()
                .map(cita -> mapper.map(cita, CitaMedicoDTO.class))
                .collect(Collectors.toList());
    }
	
	public CitaMedicoDTO obtenerCitaMedicoPorId(int id) {
        Optional<CitaMedico> citaMedicoOptional = repository.findById(id);
        return citaMedicoOptional.map(cita -> mapper.map(cita, CitaMedicoDTO.class)).orElse(null);
    }
	
	public boolean existsById(int id){
        return repository.existsById(id);
    }
}

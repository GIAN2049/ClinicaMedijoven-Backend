package com.backend.rest.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.HistoriaClinicaDTO;
import com.backend.rest.entity.HistoriaClinica;
import com.backend.rest.repository.HistoriaClinicaRepository;

@Service
public class HistoriaClinicaService extends ICRUDImpl<HistoriaClinica, Integer>{

	@Autowired
	private HistoriaClinicaRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public JpaRepository<HistoriaClinica, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	public HistoriaClinicaDTO guardar(HistoriaClinicaDTO historiaClinicaDto) {
		HistoriaClinica historiaClinica = mapper.map(historiaClinicaDto, HistoriaClinica.class);
		HistoriaClinica savedHistoriaClinica = repository.save(historiaClinica);
		HistoriaClinicaDTO historiaDto = mapper.map(savedHistoriaClinica, HistoriaClinicaDTO.class);
		return historiaDto;
	}


	public HistoriaClinicaDTO actualizarHistoriaClinica(HistoriaClinicaDTO historiaClinicaDTO) {
        Optional<HistoriaClinica> historiaOptional = repository.findById(historiaClinicaDTO.getId());

        if (historiaOptional.isPresent()) {
            HistoriaClinica historiaClinica = historiaOptional.get();
        }

        return null;
    }
	
	}


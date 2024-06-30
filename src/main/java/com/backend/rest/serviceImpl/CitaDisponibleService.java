package com.backend.rest.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.CitaDisponibleDTO;
import com.backend.rest.entity.CitaDisponible;
import com.backend.rest.repository.CitaDisponibleRepository;

@Service
public class CitaDisponibleService extends ICRUDImpl<CitaDisponible, Integer>{

	@Autowired
	private CitaDisponibleRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public JpaRepository<CitaDisponible, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	public CitaDisponibleDTO guardar(CitaDisponibleDTO citaDisponibleDto) {
		CitaDisponible citaDisponible = mapper.map(citaDisponibleDto, CitaDisponible.class);
		CitaDisponible savedCitaDisponible = repository.save(citaDisponible);
		CitaDisponibleDTO citaDto = mapper.map(savedCitaDisponible, CitaDisponibleDTO.class);
		return citaDto;
	}


	public CitaDisponibleDTO actualizarCitaDisponible(CitaDisponibleDTO citaDisponibleDTO) {
        Optional<CitaDisponible> citaOptional = repository.findById(citaDisponibleDTO.getId());

        if (citaOptional.isPresent()) {
            CitaDisponible citaDisponible = citaOptional.get();
            // Actualizar los campos necesarios
            citaDisponible.setFechaCita(citaDisponibleDTO.getFechaCita());
            citaDisponible.setHora(citaDisponibleDTO.getHora());

            CitaDisponible updatedCitaDisponible = repository.save(citaDisponible);
            return mapper.map(updatedCitaDisponible, CitaDisponibleDTO.class);
        }

        return null;
    }
	
}


package com.backend.rest.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.CitaDisponibleDTO;
import com.backend.rest.dto.CitaMedicoDTO;
import com.backend.rest.entity.CitaDisponible;
import com.backend.rest.entity.CitaMedico;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Paciente;
import com.backend.rest.repository.CitaDisponibleRepository;
import com.backend.rest.repository.CitaMedicoRepository;

import jakarta.transaction.Transactional;

@Service
public class CitaService {

	@Autowired
	private CitaDisponibleRepository citaDisponibleRepository;

	@Autowired
	private CitaMedicoRepository citaMedicoRepository;

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private ModelMapper modelMapper;
	
	
	public List<CitaDisponibleDTO> obtenerCitasDisponibles(int idMedico) {
        Medico medico = medicoService.buscarPorId(idMedico);
        List<CitaDisponible> citas = citaDisponibleRepository.findByMedicoAndIsReservadoFalse(medico);
        return citas.stream()
                    .map(cita -> modelMapper.map(cita, CitaDisponibleDTO.class))
                    .collect(Collectors.toList());
    }
	
	@Transactional
	public CitaMedicoDTO reservarCita(int idCitaDisponible, int idPaciente) {
        CitaDisponible citaDisponible = citaDisponibleRepository.findById(idCitaDisponible)
                                                                .orElseThrow(() -> new RuntimeException("Cita no disponible"));
        
        if (citaDisponible.isReservado()) {
            throw new RuntimeException("La cita ya está reservada");
        }

        citaDisponible.setReservado(true);
        citaDisponibleRepository.save(citaDisponible);
        
        Paciente paciente = pacienteService.buscarPorId(idPaciente);
        CitaMedico citaMedico = new CitaMedico();
        citaMedico.setMedico(citaDisponible.getMedico());
        citaMedico.setPaciente(paciente);
        citaMedico.setFechaCita(citaDisponible.getFechaCita());
        citaMedico.setHora(citaDisponible.getHora());
        
        citaMedicoRepository.save(citaMedico);

        return modelMapper.map(citaMedico, CitaMedicoDTO.class);
    }
}

package com.backend.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.dto.CitaMedicoDTO;
import com.backend.rest.entity.CitaMedico;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Paciente;
import com.backend.rest.serviceImpl.CitaMedicoService;
import com.backend.rest.serviceImpl.MedicoService;
import com.backend.rest.serviceImpl.PacienteService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

@RestController
@RequestMapping("apiClinica/citasMedicos")
public class CitaMedicoController {
	
	@Autowired
	private CitaMedicoService citaMedicoService;
	
	@GetMapping
	public ResponseEntity<?> obtenerTodasLasCitasMedicas(){
		List<CitaMedicoDTO> citasMedicos = citaMedicoService.obtenerTodasLasCitasMedicos();
		
		if (citasMedicos.isEmpty()) {
			return new ResponseEntity<>(
					MensajeResponse
					.builder().mensaje("No hay registros").object(null).build(),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(
					MensajeResponse
					.builder().mensaje("Registros Encontrados")
					.object(citasMedicos).build(),
					HttpStatus.OK);
		}
			
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCitaMedicoPorId(@PathVariable Integer id){
		CitaMedicoDTO citaMedico = citaMedicoService.obtenerCitaMedicoPorId(id);
		if (citaMedico == null) {
			throw new ModeloNotFoundException("El id: "+id+" no existe");
		} else {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(citaMedico).build(), HttpStatus.OK
					);
		}
	}
}

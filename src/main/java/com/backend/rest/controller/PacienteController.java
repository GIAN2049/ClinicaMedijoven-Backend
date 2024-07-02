package com.backend.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.dto.PacienteDTO;
import com.backend.rest.entity.Categoria;
import com.backend.rest.entity.Paciente;
import com.backend.rest.entity.Usuario;
import com.backend.rest.serviceImpl.PacienteService;
import com.backend.rest.serviceImpl.UsuarioService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/apiClinica/pacientes")
@CrossOrigin(origins = "http://localhost:4200")
public class PacienteController {

	
	@Autowired
	private PacienteService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<?> getAllPacientes(){
		List<Paciente> pacientes = service.listarTodos();
		
		if (pacientes.isEmpty()) {
			
			return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("No se encontraron registros")
						.object(null).build(), HttpStatus.OK
					);
		} else {
			List<PacienteDTO> pacienteDto = pacientes.stream()
					.map( p -> mapper.map(p, PacienteDTO.class))
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(pacienteDto).build(), HttpStatus.OK
				);
		}
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPacienteById(@PathVariable Integer id){
		Paciente paciente = service.buscarPorId(id);
		if (paciente == null) {
			throw new ModeloNotFoundException("Paciente con id: "+id+" no existe");
		} else {
			PacienteDTO pacienteDTO = mapper.map(paciente, PacienteDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(pacienteDTO).build(), HttpStatus.OK
				);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> registrarPaciente(@RequestBody PacienteDTO bean){
		try {
			PacienteDTO pacienteRegistrado = service.registrarPaciente(bean);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Paciente Registrado")
					.object(pacienteRegistrado).build(), HttpStatus.OK
				);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR
				);
		}
	}
	
	@PutMapping("/actualizar")
    public ResponseEntity<?> actualizarPaciente(@RequestBody PacienteDTO bean) {
		Optional<PacienteDTO> existPaciente = service.getPacienteId(
				bean.getUsuario().getId(), bean.getId());
		if(existPaciente.isPresent()) {
			
			PacienteDTO pacienteActualizado =  service.actualizarPaciente(bean);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Paciente Actualizado Correctamente")
					.object(pacienteActualizado).build(), HttpStatus.OK
				);
			
		} else {
			throw new ModeloNotFoundException("El paciente con id: "+bean.getId()+" no existe");
		}
    }
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		Paciente paciente = service.buscarPorId(id);
		if(paciente == null) {
			throw new ModeloNotFoundException("Còdigo del Paciente : "+id+" no existe");
		}
		else { 
			service.eliminar(id);
			usuarioService.eliminar(paciente.getUsuario().getId());
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

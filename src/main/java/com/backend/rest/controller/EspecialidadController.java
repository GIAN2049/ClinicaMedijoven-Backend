package com.backend.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.dto.EspecialidadDTO;
import com.backend.rest.entity.Especialidad;
import com.backend.rest.serviceImpl.EspecialidadService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

@RestController
@RequestMapping("/apiClinica/Especialidad")
public class EspecialidadController {

	@Autowired
	private EspecialidadService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarEspecialidad() {
		List<Especialidad> lst = service.listarTodos();

		if (lst.isEmpty()) {
			return new ResponseEntity<>(
					MensajeResponse
					.builder().mensaje("No hay registros").object(null).build(),
					HttpStatus.OK);
		} else {

			List<EspecialidadDTO> especialidadDTO = lst.stream().map(c -> mapper.map(c, EspecialidadDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse
					.builder().mensaje("Registros Encontrados").object(especialidadDTO).build(),
					HttpStatus.OK);
		}
	}
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<?> listarEspecialidadPorId(@PathVariable Integer id){
		Especialidad especialidad = service.buscarPorId(id);
		
		if (especialidad == null) {
			throw new ModeloNotFoundException("La especialidad con id: "+id+" no existe");
		} else {
			EspecialidadDTO especialidadDTO = mapper.map(especialidad, EspecialidadDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(especialidadDTO).build(), HttpStatus.OK
					);
					
		}
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarEspecialidad(@RequestBody EspecialidadDTO bean){
		try {
			Especialidad especialidad = mapper.map(bean, Especialidad.class);
			Especialidad especialidadObj = service.registrar(especialidad);
			EspecialidadDTO especialidadDto = mapper.map(especialidadObj, EspecialidadDTO.class);
			
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Especialidad Registrado Correctamente")
					.object(especialidadDto).build(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarEspecialidad(@RequestBody EspecialidadDTO bean){
		
		Especialidad especialidadBuscar = service.buscarPorId(bean.getId());
		
		if (especialidadBuscar == null) {
			throw new ModeloNotFoundException("La especialidad con id: "+bean.getId()+" no existe");
		} else {
			Especialidad especialidad = mapper.map(bean, Especialidad.class);
			Especialidad especialidadObj = service.actualizar(especialidad);
			EspecialidadDTO especialidadDto = mapper.map(especialidadObj, EspecialidadDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("Especialidad Actualizada Correctamente")
						.object(especialidadDto).build(), HttpStatus.OK
					);
		}
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminarEspecialidad(@PathVariable Integer id) {
		Especialidad especialidadBuscar=service.buscarPorId(id);
		if(especialidadBuscar==null)
			throw new ModeloNotFoundException("Còdigo de la especialidad : "+id+" no existe");
		else 
			service.eliminar(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
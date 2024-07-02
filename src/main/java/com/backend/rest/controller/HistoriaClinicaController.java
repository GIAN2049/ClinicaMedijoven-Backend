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

import com.backend.rest.dto.HistoriaClinicaDTO;
import com.backend.rest.entity.HistoriaClinica;
import com.backend.rest.serviceImpl.HistoriaClinicaService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

@RestController
@RequestMapping("/apiClinica/historiaClinica")
public class HistoriaClinicaController {

	@Autowired
	private HistoriaClinicaService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarHistoriaClinica() {
		List<HistoriaClinica> lst = service.listarTodos();

		if (lst.isEmpty()) {
			return new ResponseEntity<>(
					MensajeResponse
					.builder().mensaje("No hay registros").object(null).build(),
					HttpStatus.OK);
		} else {

			List<HistoriaClinicaDTO> historiaDTO = lst.stream().map(c -> mapper.map(c, HistoriaClinicaDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse
					.builder().mensaje("Registros Encontrados").object(historiaDTO).build(),
					HttpStatus.OK);
		}
	}
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<?> listarHistoriaClinicaPorId(@PathVariable Integer id){
		HistoriaClinica historia = service.buscarPorId(id);
		
		if (historia == null) {
			throw new ModeloNotFoundException("La historia con id: "+id+" no existe");
		} else {
			HistoriaClinicaDTO historiaDTO = mapper.map(historia, HistoriaClinicaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(historiaDTO).build(), HttpStatus.OK
					);
					
		}
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarEspecialidad(@RequestBody HistoriaClinicaDTO bean){
		try {
			HistoriaClinica historia = mapper.map(bean, HistoriaClinica.class);
			HistoriaClinica historiaObj = service.registrar(historia);
			HistoriaClinicaDTO historiaDto = mapper.map(historiaObj, HistoriaClinicaDTO.class);
			
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Especialidad Registrado Correctamente")
					.object(historiaDto).build(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarEspecialidad(@RequestBody HistoriaClinicaDTO bean){
		
		HistoriaClinica historiaBuscar = service.buscarPorId(bean.getId());
		
		if (historiaBuscar == null) {
			throw new ModeloNotFoundException("La historia clinica con id: "+bean.getId()+" no existe");
		} else {
			HistoriaClinica historia = mapper.map(bean, HistoriaClinica.class);
			HistoriaClinica historiaObj = service.actualizar(historia);
			HistoriaClinicaDTO historiaDto = mapper.map(historiaObj, HistoriaClinicaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("Historia Clinica Actualizada Correctamente")
						.object(historiaDto).build(), HttpStatus.OK
					);
		}
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminarHistoriaClinica(@PathVariable Integer id) {
		HistoriaClinica historiaBuscar=service.buscarPorId(id);
		if(historiaBuscar==null)
			throw new ModeloNotFoundException("Còdigo de la Historia Clinica : "+id+" no existe");
		else 
			service.eliminar(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
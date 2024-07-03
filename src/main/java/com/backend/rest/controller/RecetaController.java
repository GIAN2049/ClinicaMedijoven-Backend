package com.backend.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.dto.RecetaDTO;
import com.backend.rest.entity.Receta;
import com.backend.rest.serviceImpl.RecetaService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/apiClinica/receta")
@CrossOrigin(origins = "http://localhost:4200")
public class RecetaController {
	@Autowired
	private RecetaService servicioRec;
	@Autowired
	private ModelMapper mapper;
	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		List<Receta> lista = servicioRec.listarTodos();
		if (lista == null) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("No hay registros")
					.object(null).build(),HttpStatus.OK);
		} else {
			List<RecetaDTO> data = lista.stream().map(r ->
					mapper.map(r, RecetaDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros")
					.object(data).build(),HttpStatus.OK);
		}
	}
	@GetMapping("/consulta/{codigo}")
	public ResponseEntity<?> conuslta(@PathVariable Integer codigo) {
		Receta recBuscar = servicioRec.buscarPorId(codigo);
		if (recBuscar == null)
			throw new ModeloNotFoundException("Código de la receta: " + codigo + " no existe");
		else {
			RecetaDTO dto = mapper.map(recBuscar, RecetaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registro existe")
					.object(dto).build(),HttpStatus.OK);
		}
	}
	@PostMapping("/grabar")
	public ResponseEntity<?> grabar(@Valid @RequestBody RecetaDTO bean) {
		try {
			Receta rec = mapper.map(bean, Receta.class);
			Receta r = servicioRec.registrar(rec);
			RecetaDTO dto = mapper.map(r, RecetaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Guardado correctamente")
					.object(dto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizar(@Valid @RequestBody RecetaDTO bean) {
		Receta recBuscar = servicioRec.buscarPorId(bean.getId());
		if (recBuscar == null)
			throw new ModeloNotFoundException("Código de la receta: " + bean.getId() + " no existe");
		else {
			Receta rec = mapper.map(bean, Receta.class);
			Receta r = servicioRec.registrar(rec);
			RecetaDTO dto = mapper.map(r, RecetaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registro actualizado")
					.object(dto).build(),HttpStatus.OK);
		}
	}
	@DeleteMapping("/eliminar/{codigo}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer codigo) {
		Receta recBuscar = servicioRec.buscarPorId(codigo);
		if (recBuscar == null)
			throw new ModeloNotFoundException("Código de la receta: " + codigo + " no existe");
		else
			servicioRec.eliminar(codigo);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/consultaMed/{codigo}")
	public ResponseEntity<?> consultaMed(@PathVariable Integer codigo) {
		List<Receta> lista = servicioRec.listarPorMedico(codigo);
		List<RecetaDTO> data = lista.stream().map(r->
		mapper.map(r, RecetaDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	@GetMapping("/consutaPac/{codigo}")
	public ResponseEntity<?> consultaPac(@PathVariable Integer codigo) {
		List<Receta> lista = servicioRec.listarPorPaciente(codigo);
		List<RecetaDTO> data = lista.stream().map(r->
		mapper.map(r, RecetaDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
}
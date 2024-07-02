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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.dto.MedicamentoDTO;
import com.backend.rest.entity.Medicamento;
import com.backend.rest.serviceImpl.MedicamentoService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apiClinica/medicamento")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicamentoController {
	@Autowired
	private MedicamentoService servicioMed;
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		List<Medicamento>lista=servicioMed.listarTodos();
		//validar lista
		if(lista==null) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("No hay registros")
					.object(null).build(),HttpStatus.OK);
		}
		else {
			List<MedicamentoDTO> data=lista.stream().map(m->   
				mapper.map(m, MedicamentoDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("registros")
					.object(data).build(),HttpStatus.OK);		
		}
		
	}
	
	@GetMapping("/consulta/{codigo}")
	public ResponseEntity<?> consulta(@PathVariable("codigo") Integer codigo) {
		//buscar si existe el còdigo
		Medicamento medBuscar=servicioMed.buscarPorId(codigo);
		//validar
		if(medBuscar==null)
			throw new ModeloNotFoundException("Còdigo del medicamento : "+codigo+" no existe");
		else {
			MedicamentoDTO dto=mapper.map(medBuscar, MedicamentoDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registro existe")
					.object(dto).build(),HttpStatus.OK);
		}
	}
	@PostMapping("/grabar")
	public  ResponseEntity<?>  grabar(@Valid @RequestBody MedicamentoDTO bean){
		try {
			Medicamento med=mapper.map(bean, Medicamento.class);
			Medicamento m=servicioMed.registrar(med);
			MedicamentoDTO dto=mapper.map(m, MedicamentoDTO.class);
			
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
	public ResponseEntity<?> actualizar(@Valid @RequestBody MedicamentoDTO bean){
		//buscar si existe el còdigo
		Medicamento medBuscar=servicioMed.buscarPorId(bean.getId());
		//validar
		if(medBuscar==null)
			throw new ModeloNotFoundException("Còdigo del medicamento : "+bean.getId()+" no existe");
		else {
			Medicamento med=mapper.map(bean, Medicamento.class);
			Medicamento m=servicioMed.actualizar(med);
			MedicamentoDTO dto=mapper.map(m, MedicamentoDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registro actualizado")
					.object(dto).build(),HttpStatus.OK);
		}
		
	}
	@DeleteMapping("/eliminar/{codigo}")
	public ResponseEntity<Void> eliminar(@PathVariable("codigo") Integer codigo) {
		Medicamento medBuscar=servicioMed.buscarPorId(codigo);
		if(medBuscar==null)
			throw new ModeloNotFoundException("Còdigo del medicamento : "+codigo+" no existe");
		else 
			servicioMed.eliminar(codigo);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}

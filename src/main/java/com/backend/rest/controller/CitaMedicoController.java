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

import com.backend.rest.dto.CategoriaDTO;
import com.backend.rest.dto.CitaMedicoDTO;
import com.backend.rest.entity.Categoria;
import com.backend.rest.entity.CitaMedico;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Paciente;
import com.backend.rest.serviceImpl.CitaMedicoService;
import com.backend.rest.serviceImpl.MedicoService;
import com.backend.rest.serviceImpl.PacienteService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

@RestController
@RequestMapping("/apiClinica/citasMedicos")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaMedicoController {
	
	@Autowired
	private CitaMedicoService citaMedicoService;
	
	@Autowired
	private ModelMapper mapper;
	
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
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarCita(@RequestBody CitaMedicoDTO bean){ 
		try {
			CitaMedico cita = mapper.map(bean, CitaMedico.class);
			CitaMedico citaObj = citaMedicoService.registrar(cita);
			CitaMedicoDTO citaDto = mapper.map(citaObj, CitaMedicoDTO.class);
			
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Cia Registrado Correctamente")
					.object(citaDto).build(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(MensajeResponse.builder() 
					.mensaje(e.getMessage())
					.object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarCita(@RequestBody CitaMedicoDTO bean){
		
		CitaMedico citaBuscar = citaMedicoService.buscarPorId(bean.getId());
		
		if (citaBuscar == null) {
			throw new ModeloNotFoundException("La cita con id: "+bean.getId()+" no existe");
		} else {
			CitaMedico cita = mapper.map(bean, CitaMedico.class);
			CitaMedico citaObj = citaMedicoService.actualizar(cita);
			CitaMedicoDTO citaDto = mapper.map(citaObj, CitaMedicoDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("Cita Actualizada Correctamente")
						.object(citaDto).build(), HttpStatus.OK
					);
		}
	}
	
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminarCita(@PathVariable Integer id) {
		CitaMedico citaBuscar=citaMedicoService.buscarPorId(id);
		if(citaBuscar==null)
			throw new ModeloNotFoundException("Còdigo de la cita : "+id+" no existe");
		else 
			citaMedicoService.eliminar(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
}
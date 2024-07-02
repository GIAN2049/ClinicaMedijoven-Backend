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

import com.backend.rest.dto.CategoriaDTO;
import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.dto.UsuarioDTO;
import com.backend.rest.entity.Categoria;
import com.backend.rest.entity.Medico;
import com.backend.rest.serviceImpl.MedicoService;
import com.backend.rest.serviceImpl.UsuarioHasRolService;
import com.backend.rest.serviceImpl.UsuarioService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/apiClinica/medicos")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicoController {

	@Autowired
	private MedicoService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioHasRolService usuarioHasRolService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<?> getAllMedicos(){
		List<Medico> medicos =  service.listarTodos();
		
		if (medicos.isEmpty()) {
			return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("No se Encontraron Registros")
						.object(null).build(), HttpStatus.OK
					);
		} else {
			List<MedicoDTO> medicoDto = medicos.stream()
					.map(m -> mapper.map(m, MedicoDTO.class)).collect(Collectors.toList());
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(medicoDto).build(), HttpStatus.OK
				);
		}
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<?> getMedicoById(@PathVariable Integer id){
		Medico medico = service.buscarPorId(id);
		if (medico == null) {
			throw new ModeloNotFoundException("Medico con id: "+id+" no existe");
		} else {
			MedicoDTO medicoDTO = mapper.map(medico, MedicoDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(medicoDTO).build(), HttpStatus.OK
				);
		}
	}
	
	@PostMapping
    public ResponseEntity<?> registrarMedico(@RequestBody MedicoDTO medicoDTO) {
		try {
			MedicoDTO medicoRegistrado = service.registrarMedico(medicoDTO);
			
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Medico Registrado")
					.object(medicoRegistrado).build(), HttpStatus.OK
				);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR
				);
		}
        
        
    }
	
	@PutMapping("/actualizar")
    public ResponseEntity<?> actualizarMedico(@RequestBody MedicoDTO medicoDTO) {
		Medico medicoExist = service.buscarPorId(medicoDTO.getId());
		if(medicoExist == null) {
			throw new ModeloNotFoundException("El paciente con id: "+medicoDTO.getId()+" no existe");
		} else {
			MedicoDTO medicoActualizado =  service.actualizarMedico(medicoDTO);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Paciente Actualizado Correctamente")
					.object(medicoActualizado).build(), HttpStatus.OK
				);
		}
    }
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		Medico medico = service.buscarPorId(id);
		if(medico == null) {
			throw new ModeloNotFoundException("Còdigo del Medico : "+id+" no existe");
		}
		else { 
			service.eliminar(id);
			usuarioService.eliminar(medico.getUsuario().getId());
			usuarioHasRolService.deleteByUsuarioId(medico.getUsuario().getId());
			
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
}

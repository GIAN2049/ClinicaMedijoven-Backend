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
import com.backend.rest.dto.UsuarioDTO;
import com.backend.rest.entity.Categoria;
import com.backend.rest.entity.Paciente;
import com.backend.rest.entity.Usuario;
import com.backend.rest.serviceImpl.PacienteService;
import com.backend.rest.serviceImpl.UsuarioHasRolService;
import com.backend.rest.serviceImpl.UsuarioService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/apiClinica/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {


	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioHasRolService usuarioHasRolService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<?> getAllUsuarios(){
		List<Usuario> usuario = service.listarTodos();
		
		if (usuario.isEmpty()) {
			
			return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("No se encontraron registros")
						.object(null).build(), HttpStatus.OK
					);
		} else {
			List<UsuarioDTO> usuarioDto = usuario.stream()
					.map( p -> mapper.map(p, UsuarioDTO.class))
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(usuarioDto).build(), HttpStatus.OK
				);
		}
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUsuarioById(@PathVariable Integer id){
		Usuario usuario = service.buscarPorId(id);
		if (usuario == null) {
			throw new ModeloNotFoundException("Usuario con id: "+id+" no existe");
		} else {
			UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(usuarioDTO).build(), HttpStatus.OK
				);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDTO bean){
		try {
			UsuarioDTO usuarioRegistrado = service.registrar(bean);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Usuario Registrado")
					.object(usuarioRegistrado).build(), HttpStatus.OK
				);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR
				);
		}
	}
	
	@PutMapping("/actualizar")
    public ResponseEntity<?> actualizarUsuario(@RequestBody UsuarioDTO bean) {
		Optional<UsuarioDTO> existUsuario = service.getUsuarioId(
				 bean.getId());
		if(existUsuario.isPresent()) {
			UsuarioDTO usuarioActualizado =  service.actualizar(bean);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Usuario Actualizado Correctamente")
					.object(usuarioActualizado).build(), HttpStatus.OK
				);
			
		} else {
			throw new ModeloNotFoundException("El usuario con id: "+bean.getId()+" no existe");
		}
    }
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		Usuario usuario = service.buscarPorId(id);
		if(usuario == null) {
			throw new ModeloNotFoundException("Còdigo del Usuario : "+id+" no existe");
		}
		else { 
			service.eliminar(id);
			usuarioHasRolService.deleteByUsuarioId(id);
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

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

import com.backend.rest.dto.CategoriaDTO;
import com.backend.rest.entity.Categoria;
import com.backend.rest.serviceImpl.CategoriaService;
import com.backend.rest.utils.MensajeResponse;
import com.backend.rest.utils.ModeloNotFoundException;

@RestController
@RequestMapping("/apiClinica/Categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listarCategorias() {
		List<Categoria> lst = service.listarTodos();

		if (lst.isEmpty()) {
			return new ResponseEntity<>(
					MensajeResponse
					.builder().mensaje("No hay registros").object(null).build(),
					HttpStatus.OK);
		} else {

			List<CategoriaDTO> categoriaDTO = lst.stream().map(c -> mapper.map(c, CategoriaDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse
					.builder().mensaje("Registros Encontrados").object(categoriaDTO).build(),
					HttpStatus.OK);
		}
	}
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<?> listarCategoriasPorId(@PathVariable Integer id){
		Categoria categoria = service.buscarPorId(id);
		
		if (categoria == null) {
			throw new ModeloNotFoundException("La categoria con id: "+id+" no existe");
		} else {
			CategoriaDTO categoriaDTO = mapper.map(categoria, CategoriaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(categoriaDTO).build(), HttpStatus.OK
					);
					
		}
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarCategoria(@RequestBody CategoriaDTO bean){
		try {
			Categoria categoria = mapper.map(bean, Categoria.class);
			Categoria categoriaObj = service.registrar(categoria);
			CategoriaDTO categoriaDto = mapper.map(categoriaObj, CategoriaDTO.class);
			
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Categoria Registrado Correctamente")
					.object(categoriaDto).build(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarCategoria(@RequestBody CategoriaDTO bean){
		
		Categoria categoriaBuscar = service.buscarPorId(bean.getId());
		
		if (categoriaBuscar == null) {
			throw new ModeloNotFoundException("La categoria con id: "+bean.getId()+" no existe");
		} else {
			Categoria categoria = mapper.map(bean, Categoria.class);
			Categoria categoriaObj = service.actualizar(categoria);
			CategoriaDTO categoriaDto = mapper.map(categoriaObj, CategoriaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("Categoria Actualizada Correctamente")
						.object(categoriaDto).build(), HttpStatus.OK
					);
		}
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
		Categoria categoriaBuscar=service.buscarPorId(id);
		if(categoriaBuscar==null)
			throw new ModeloNotFoundException("Còdigo del medicamento : "+id+" no existe");
		else 
			service.eliminar(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

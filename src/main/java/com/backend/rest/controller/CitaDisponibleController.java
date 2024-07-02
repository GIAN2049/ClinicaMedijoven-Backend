package com.backend.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.dto.CitaDisponibleDTO;
import com.backend.rest.serviceImpl.CitaDisponibleService;
import com.backend.rest.utils.MensajeResponse;

@RestController
@RequestMapping("/apiClinica/citasDisponibles")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaDisponibleController {

	
	@Autowired
	private CitaDisponibleService service;
	
	@PostMapping
	public ResponseEntity<?> agregarCitaDisponible(@RequestBody CitaDisponibleDTO bean) {
		try {
			
			CitaDisponibleDTO citaAgregada = service.guardar(bean);
			return new ResponseEntity<>(
					MensajeResponse.builder()
					.mensaje("Se agregó la cita disponible correctamente")
					.object(citaAgregada).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(),
					HttpStatus.OK);
		}
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarCita(@RequestBody CitaDisponibleDTO bean){
		
			CitaDisponibleDTO citaActualizada  = service.actualizarCitaDisponible(bean);
			
			if (citaActualizada != null) {
				return new ResponseEntity<>(
						MensajeResponse.builder()
						.mensaje("Se Actualizo la reserva de cita")
						.object(citaActualizada).build(),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						MensajeResponse.builder()
						.mensaje("No se Encontro la Cita disponible")
						.object(null).build(),
						HttpStatus.OK);
			}
	}

	
}

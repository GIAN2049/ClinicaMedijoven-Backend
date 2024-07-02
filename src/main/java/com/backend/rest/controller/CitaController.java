package com.backend.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.dto.CitaDisponibleDTO;
import com.backend.rest.dto.CitaMedicoDTO;
import com.backend.rest.serviceImpl.CitaService;
import com.backend.rest.utils.MensajeResponse;

@RestController
@RequestMapping("/apiClinica/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;
    
    @GetMapping("/disponibles/{idMedico}")
    public ResponseEntity<?> obtenerCitasDisponibles(@PathVariable int idMedico){
    	List<CitaDisponibleDTO> citas = citaService.obtenerCitasDisponibles(idMedico);
    	
    	if (citas.isEmpty()) {
			return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("No se encontraron registros")
						.object(null).build(),
						HttpStatus.OK
					);
		} else {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Registros Encontrados")
					.object(citas).build(),
					HttpStatus.OK
				);
		}
    }
    
    @PostMapping("/reservar/{idCitaDisponible}/{idPaciente}")
    public ResponseEntity<?> reservarCita(@PathVariable int idCitaDisponible, @PathVariable int idPaciente){
    	try {
			CitaMedicoDTO citaReservada = citaService.reservarCita(idCitaDisponible, idPaciente);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Cita Registrada")
					.object(citaReservada).build(),
					HttpStatus.OK
				);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage())
					.object(null).build(),
					HttpStatus.OK
				);
		}
    }
    
}

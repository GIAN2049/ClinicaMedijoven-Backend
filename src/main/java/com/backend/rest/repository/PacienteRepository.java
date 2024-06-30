package com.backend.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.rest.dto.PacienteDTO;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Integer>{
	@Query("SELECT p FROM Paciente p where p.usuario.id = ?1 and p.id = ?2")
    Optional<Paciente> getPaciente(int idUsuario, int idPaciente);
}

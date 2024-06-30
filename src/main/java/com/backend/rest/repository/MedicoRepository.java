package com.backend.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.rest.entity.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Integer>{
	
	@Query("SELECT m FROM Medico m where m.usuario.id = ?1 and m.id = ?2")
    Optional<Medico> getMedico(int idUsuario, int idMedico);
}

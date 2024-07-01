package com.backend.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.rest.entity.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Integer>{
	@Query("select r from Receta r where r.medico=?1")
	public List<Receta> findAllRecetaByMedico(Integer cod);
	@Query("select r from Receta r where r.paciente=?1")
	public List<Receta> findAllRecetaByPaciente(Integer cod);
}
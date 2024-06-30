package com.backend.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.entity.CitaDisponible;
import com.backend.rest.entity.Medico;

public interface CitaDisponibleRepository extends JpaRepository<CitaDisponible, Integer>{
	List<CitaDisponible> findByMedicoAndIsReservadoFalse(Medico medico);
}

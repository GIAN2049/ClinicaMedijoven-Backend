package com.backend.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.rest.entity.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer>{

}

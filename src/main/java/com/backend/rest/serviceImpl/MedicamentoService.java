package com.backend.rest.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.Medicamento;
import com.backend.rest.repository.MedicamentoRepository;

@Service
public class MedicamentoService extends ICRUDImpl<Medicamento, Integer>{
	@Autowired
	private MedicamentoRepository repo;


	@Override
	public JpaRepository<Medicamento, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repo;
	}

}

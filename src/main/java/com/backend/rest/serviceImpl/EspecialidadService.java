package com.backend.rest.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.Especialidad;
import com.backend.rest.repository.EspecialidadRepository;

@Service
public class EspecialidadService extends ICRUDImpl<Especialidad, Integer>{

	@Autowired
	private EspecialidadRepository repository;
	
	@Override
	public JpaRepository<Especialidad, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}

}

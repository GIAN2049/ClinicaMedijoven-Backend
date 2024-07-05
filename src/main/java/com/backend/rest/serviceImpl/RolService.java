package com.backend.rest.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.Rol;
import com.backend.rest.repository.RolRepository;

@Service
public class RolService extends ICRUDImpl<Rol, Integer>{

	@Autowired
	private RolRepository repository;
	
	@Override
	public JpaRepository<Rol, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}

}

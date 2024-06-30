
package com.backend.rest.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.Categoria;
import com.backend.rest.repository.CategoriaRepository;

@Service
public class CategoriaService extends ICRUDImpl<Categoria, Integer>{

	@Autowired
	private CategoriaRepository repository;
	
	@Override
	public JpaRepository<Categoria, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
}

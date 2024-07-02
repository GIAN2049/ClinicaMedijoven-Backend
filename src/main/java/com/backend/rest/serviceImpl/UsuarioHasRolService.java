package com.backend.rest.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.UsuarioHasRol;
import com.backend.rest.entity.UsuarioHasRolPK;
import com.backend.rest.repository.UsuarioHasRolRepository;

@Service
public class UsuarioHasRolService extends ICRUDImpl<UsuarioHasRol, UsuarioHasRolPK>{

	@Autowired
	private UsuarioHasRolRepository repository;
	
	@Override
	public JpaRepository<UsuarioHasRol, UsuarioHasRolPK> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}

	
	public void deleteByUsuarioId(Integer usuarioId) {
		repository.deleteByUsuarioId(usuarioId);
	}
}

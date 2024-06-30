package com.backend.rest.serviceImpl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rest.service.ICRUD;

public abstract class ICRUDImpl<T, ID> implements ICRUD<T, ID> {

	public abstract JpaRepository<T, ID> getRepository();
	
	@Override
	public T registrar(T bean) {
		// TODO Auto-generated method stub
		return getRepository().save(bean);
	}

	@Override
	public T actualizar(T bean) {
		// TODO Auto-generated method stub
		return getRepository().save(bean);
	}

	@Override
	public void eliminar(ID cod) {
		// TODO Auto-generated method stub
		getRepository().deleteById(cod);
	}

	@Override
	public T buscarPorId(ID cod) {
		// TODO Auto-generated method stub
		return getRepository().findById(cod).orElse(null);
	}

	@Override
	public List<T> listarTodos() {
		// TODO Auto-generated method stub
		return getRepository().findAll();
	}

}

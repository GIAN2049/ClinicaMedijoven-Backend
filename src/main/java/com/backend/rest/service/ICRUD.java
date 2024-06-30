package com.backend.rest.service;

import java.util.List;

public interface ICRUD<T,ID> {
	
	T registrar(T bean);
	T actualizar(T bean);
	void eliminar(ID cod);
	T buscarPorId(ID cod);
	List<T> listarTodos();
}

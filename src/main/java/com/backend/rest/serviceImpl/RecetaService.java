package com.backend.rest.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.Medicamento;
import com.backend.rest.entity.Receta;
import com.backend.rest.repository.RecetaRepository;

@Service
public class RecetaService extends ICRUDImpl<Receta, Integer> {
	@Autowired
	RecetaRepository repo;

	@Override
	public JpaRepository<Receta, Integer> getRepository() {
		// TODO Auto-generated method stub
		return repo;
	}
	
	public List<Receta> listarPorMedico(Integer cod) {
		return repo.findAllRecetaByMedico(cod);
	}
	
	public List<Receta> listarPorPaciente(Integer cod) {
		return repo.findAllRecetaByPaciente(cod);
	}
	
	public List<Medicamento> listarMedicamentoPorCategoria(int idCategoria){
		return repo.findMedicamentoByIdCategoria(idCategoria);
	}

}
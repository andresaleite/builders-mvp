package br.com.builders.mvp.andresa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.builders.mvp.andresa.domain.Estado;
import br.com.builders.mvp.andresa.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> find() {
		return repo.findAllByOrderByNome();
		
	}
}

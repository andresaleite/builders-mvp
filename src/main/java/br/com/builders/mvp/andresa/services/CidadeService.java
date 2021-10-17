package br.com.builders.mvp.andresa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.builders.mvp.andresa.domain.Cidade;
import br.com.builders.mvp.andresa.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> find(Integer idEstado) {
		
		return repo.findCidades(idEstado);
		
	}
	
}

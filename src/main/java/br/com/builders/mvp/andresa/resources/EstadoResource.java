package br.com.builders.mvp.andresa.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.builders.mvp.andresa.domain.Cidade;
import br.com.builders.mvp.andresa.domain.Estado;
import br.com.builders.mvp.andresa.dto.CidadeDTO;
import br.com.builders.mvp.andresa.dto.EstadoDTO;
import br.com.builders.mvp.andresa.services.CidadeService;
import br.com.builders.mvp.andresa.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService serv;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> find() {
		List<Estado> lista = serv.find();
		List<EstadoDTO> listDto = lista.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{idEstado}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> find(@PathVariable Integer idEstado) {
		List<Cidade> lista = cidadeService.find(idEstado);
		List<CidadeDTO> listDto = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}

}
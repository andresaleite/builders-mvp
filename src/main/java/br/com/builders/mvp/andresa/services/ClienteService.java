package br.com.builders.mvp.andresa.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.builders.mvp.andresa.domain.Cidade;
import br.com.builders.mvp.andresa.domain.Cliente;
import br.com.builders.mvp.andresa.domain.Endereco;
import br.com.builders.mvp.andresa.domain.enums.Perfil;
import br.com.builders.mvp.andresa.domain.enums.TipoCliente;
import br.com.builders.mvp.andresa.dto.ClienteUpdateDTO;
import br.com.builders.mvp.andresa.dto.ClienteUpdateDTO;
import br.com.builders.mvp.andresa.dto.ClienteUpdateDTO;
import br.com.builders.mvp.andresa.dto.ClienteDTO;
import br.com.builders.mvp.andresa.dto.ClienteNewDTO;
import br.com.builders.mvp.andresa.repositories.ClienteRepository;
import br.com.builders.mvp.andresa.repositories.EnderecoRepository;
import br.com.builders.mvp.andresa.security.UserSS;
import br.com.builders.mvp.andresa.services.exceptions.AuthorizationException;
import br.com.builders.mvp.andresa.services.exceptions.DataIntegrityException;
import br.com.builders.mvp.andresa.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName(), null));
		
	}
	
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Cliente findByCpf(String cpf) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN)) {
			throw new AuthorizationException("Acesso negado. É preciso ser administrador para pesquisar pelo CPF.");
		}
	
		Cliente obj = repo.findByCpfOuCnpj(cpf);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction),	orderBy);
		return repo.findAll(pageRequest);
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
		
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.saveAndFlush(obj);
		enderecoRepo.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = this.find(obj.getId());
		updateData(newObj, obj);
		return (Cliente) repo.saveAndFlush(newObj);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setSenha(obj.getSenha());
		newObj.setEnderecos(obj.getEnderecos());
		newObj.setTelefones(obj.getTelefones());
		newObj.setDataNascimento(obj.getDataNascimento());
	}

	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir pois há entidades relacionadas.");
		}
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null, null); 
	}
	
	public Cliente fromNewDTO(ClienteNewDTO objNewDTO) {
		Cliente cli 	= new Cliente(null, objNewDTO.getNome(), objNewDTO.getEmail(), objNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(objNewDTO.getTipo()), pe.encode(objNewDTO.getSenha()), objNewDTO.getDataNascimento());
		
		Cidade cid		= new Cidade(objNewDTO.getCidadeId(), null, null);
		
		Endereco end 	= new Endereco(null, objNewDTO.getLogradouro(), 
						objNewDTO.getNumero(), objNewDTO.getComplemento(), 
						objNewDTO.getBairro(), objNewDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().addAll(Arrays.asList(objNewDTO.getTelefone1(), objNewDTO.getTelefone2(), objNewDTO.getTelefone3()));
		
		return cli;
	}
	
	public Cliente fromAlteracaoDTO(ClienteUpdateDTO objAlteracaoDTO) {
		Cliente cli 	= new Cliente(objAlteracaoDTO.getId(), objAlteracaoDTO.getNome(), objAlteracaoDTO.getEmail(), objAlteracaoDTO.getCpfOuCnpj(), TipoCliente.toEnum(objAlteracaoDTO.getTipo()), pe.encode(objAlteracaoDTO.getSenha()), objAlteracaoDTO.getDataNascimento());
		
		Cidade cid		= new Cidade(objAlteracaoDTO.getCidadeId(), null, null);
		
		Endereco end 	= new Endereco(null, objAlteracaoDTO.getLogradouro(), 
						objAlteracaoDTO.getNumero(), objAlteracaoDTO.getComplemento(), 
						objAlteracaoDTO.getBairro(), objAlteracaoDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().addAll(Arrays.asList(objAlteracaoDTO.getTelefone1(), objAlteracaoDTO.getTelefone2(), objAlteracaoDTO.getTelefone3()));
		
		return cli;
	}
	
}

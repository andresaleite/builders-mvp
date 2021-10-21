package br.com.builders.mvp.andresa.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.builders.mvp.andresa.domain.Cidade;
import br.com.builders.mvp.andresa.domain.Cliente;
import br.com.builders.mvp.andresa.domain.Endereco;
import br.com.builders.mvp.andresa.domain.Estado;
import br.com.builders.mvp.andresa.domain.enums.Perfil;
import br.com.builders.mvp.andresa.domain.enums.TipoCliente;
import br.com.builders.mvp.andresa.repositories.CidadeRepository;
import br.com.builders.mvp.andresa.repositories.ClienteRepository;
import br.com.builders.mvp.andresa.repositories.EnderecoRepository;
import br.com.builders.mvp.andresa.repositories.EstadoRepository;

@Service
public class DBService {
	
	@Autowired
	private CidadeRepository cidadeRepo;	
	@Autowired
	private EstadoRepository estadoRepo;
	@Autowired
	private EnderecoRepository enderecoRepo;
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantiateTestDatabase() throws ParseException {

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		Estado est3 = new Estado(null, "Ceará");
		
		Cidade cidade1 = new Cidade(null, "Uberlândia", est1);
		Cidade cidade2 = new Cidade(null, "São Paulo", est2);
		Cidade cidade3 = new Cidade(null, "Campinas", est2);
		Cidade cidade4 = new Cidade(null, "Fortaleza", est3);
		Cidade cidade5 = new Cidade(null, "Ibiapina", est3);
		Cidade cidade6 = new Cidade(null, "Machacalis", est1);
		
		est1.getCidades().addAll(Arrays.asList(cidade1));
		est2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		est3.getCidades().addAll(Arrays.asList(cidade4, cidade5));

		estadoRepo.saveAll(Arrays.asList(est1, est2, est3));
		cidadeRepo.saveAll(Arrays.asList(cidade1, cidade2, cidade3, cidade4, cidade5, cidade6));
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Cliente cli1 = new Cliente(null, "Andresa Leite L Prates", "andresaleite@gmail.com", "90765788187", TipoCliente.PESSOAFISICA, pe.encode("123"),  (Date) dateFormat.parse("30/09/1980"));
		cli1.getTelefones().addAll(Arrays.asList("55123456","661234566","551354544"));
		cli1.addPerfil(Perfil.ADMIN);
		
		Cliente cli2 = new Cliente(null, "Elizabeth P Leite Lopes", "bethleitel@gmail.com", "31628382740", TipoCliente.PESSOAFISICA, pe.encode("123"), (Date) dateFormat.parse("15/04/1959"));
		cli2.getTelefones().addAll(Arrays.asList("93883321", "34252625"));
		cli2.addPerfil(Perfil.ADMIN);
		
		Cliente cli3 = new Cliente(null, "Alzir Lopes", "alzir@gmail.com", "72952945039", TipoCliente.PESSOAFISICA, pe.encode("123"), (Date) dateFormat.parse("18/10/1954"));
		cli3.getTelefones().addAll(Arrays.asList("515152222", "87878787"));
		
		Cliente cli4 = new Cliente(null, "Guilherme", "guilherme@gmail.com", "50917328027", TipoCliente.PESSOAFISICA, pe.encode("123"), (Date) dateFormat.parse("06/07/2009"));
		cli3.getTelefones().addAll(Arrays.asList("509173280", "1161884455"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "33225511", cli1, cidade1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "5588888", cli1, cidade2);
		Endereco e3 = new Endereco(null, "Avenida Arvores", "22", "Sala 30", "fora", "5588888", cli2, cidade2);
		Endereco e4 = new Endereco(null, "Avenida Arvores", "22", "Sala 30", "fora", "5588888", cli3, cidade2);
		Endereco e5 = new Endereco(null, "Amoreira Barros", "05", "Xxxx", "Perto da Pracinha", "123", cli4, cidade6);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		cli3.getEnderecos().addAll(Arrays.asList(e4));
		cli4.getEnderecos().addAll(Arrays.asList(e5));
		clienteRepo.saveAll(Arrays.asList(cli1, cli2, cli3, cli4));
		enderecoRepo.saveAll(Arrays.asList(e1, e2, e3, e4, e5));
	}

}

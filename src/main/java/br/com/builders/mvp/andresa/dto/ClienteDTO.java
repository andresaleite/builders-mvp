package br.com.builders.mvp.andresa.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.builders.mvp.andresa.domain.Cliente;
import br.com.builders.mvp.andresa.services.validations.ClienteUpdate;
import br.com.builders.mvp.andresa.util.Util;

@ClienteUpdate
public class ClienteDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "O preenchimento do nome é obrigatório.")
	@Length(min = 5, max=120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message = "O preenchimento do e-mail é obrigatório.")
	@Email(message = "E-mail inválido.")
	private String email;
	
	private Date dataNascimento;	
	private int idade;
	private boolean aniversario;
	private String cpfOuCnpj;
	
	public ClienteDTO() {
	}
	
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.dataNascimento = obj.getDataNascimento();
		this.cpfOuCnpj = obj.getCpfOuCnpj();
		
	}

	public int getIdade() {
		if(getDataNascimento() != null) {
			return Util.calculoIdade(getDataNascimento());
		}
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public boolean isAniversario() {
		if(getDataNascimento() != null) {
			return Util.comparaDiaEMes(getDataNascimento(), new Date());
		}
		return aniversario;
	}

	public void setAniversario(boolean aniversario) {
		this.aniversario = aniversario;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

}

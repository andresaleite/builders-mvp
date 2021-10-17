package br.com.builders.mvp.andresa.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.builders.mvp.andresa.domain.Cliente;
import br.com.builders.mvp.andresa.domain.enums.TipoCliente;
import br.com.builders.mvp.andresa.dto.ClienteNewDTO;
import br.com.builders.mvp.andresa.repositories.ClienteRepository;
import br.com.builders.mvp.andresa.resources.exceptions.FieldMessage;
import br.com.builders.mvp.andresa.services.validations.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	 public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}else if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente cEmail = repo.findByEmail(objDto.getEmail());
		if(cEmail != null) {
			list.add(new FieldMessage("email", "O e-mail já foi cadastrado."));
		}
		
		ArrayList<Cliente>  listaCpfCnpj = repo.findByCpfOuCnpj(objDto.getCpfOuCnpj());
		if(listaCpfCnpj != null && listaCpfCnpj.size() > 0) {
			list.add(new FieldMessage("cpfOuCnpj", "O CPF ou CNPJ já foi cadastrado."));
		}
		
		for (FieldMessage e : list) {
			 context.disableDefaultConstraintViolation();
			 context.buildConstraintViolationWithTemplate(e.getMessage())
			 .addPropertyNode(e.getFieldName()).addConstraintViolation();
		 }
		 return list.isEmpty();
	}
}
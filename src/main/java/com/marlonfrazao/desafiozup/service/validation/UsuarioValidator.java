package com.marlonfrazao.desafiozup.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.marlonfrazao.desafiozup.dto.UsuarioDTO;
import com.marlonfrazao.desafiozup.repositories.UsuarioRepository;
import com.marlonfrazao.desafiozup.resources.exceptions.FieldMessage;

public class UsuarioValidator implements ConstraintValidator<UsuarioValid, UsuarioDTO> {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public void initialize(UsuarioValid ann) {
	}
	
	@Override
	public boolean isValid(UsuarioDTO dto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();
		
		if(repository.findByEmail(dto.getEmail()) != null) {
			list.add(new FieldMessage("email", "Já existe um usuário cadastrado com esse e-mail"));
		}
		
		if(repository.findByCpf(dto.getCpf()) != null) {
			list.add(new FieldMessage("cpf","Já existe um usuário cadastrado com esse CPF!"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}

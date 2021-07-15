package com.marlonfrazao.desafiozup.dto;

import java.util.Date;
import java.util.Set;

import com.marlonfrazao.desafiozup.entities.Endereco;
import com.marlonfrazao.desafiozup.entities.Usuario;

public class UsuarioResponseDTO extends UsuarioFormDTO {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	public UsuarioResponseDTO() {
		super();
	}
	
	public UsuarioResponseDTO(String nome, String email, String cpf, Date dataNascimento, Long id) {
		super(nome, email, cpf, dataNascimento);
		this.id = id;
	}

	public UsuarioResponseDTO(Usuario entity) {
		super(entity);
		id = entity.getId();
	}
	
	public UsuarioResponseDTO(Usuario entity, Set<Endereco> enderecos) {
		super(entity, enderecos);
		id = entity.getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

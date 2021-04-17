package com.marlonfrazao.desafiozup.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.marlonfrazao.desafiozup.entities.Endereco;
import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.service.validation.UsuarioValid;

@UsuarioValid
public class UsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private Date dataNascimento;
	
	Set<EnderecoDTO> enderecos = new HashSet<>();
	
	public UsuarioDTO() {}
	
	public UsuarioDTO(Long id, String nome, String email, String cpf, Date dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}
	
	public UsuarioDTO(Usuario entity) {
		id = entity.getId();
		nome = entity.getNome();
		email = entity.getEmail();
		cpf = entity.getCpf();
		dataNascimento = entity.getDataNascimento();
	}
	
	public UsuarioDTO(Usuario entity, Set<Endereco> enderecos) {
		this(entity);
		enderecos.forEach(e -> this.enderecos.add(new EnderecoDTO(e)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Set<EnderecoDTO> getEnderecos() {
		return enderecos;
	}
}

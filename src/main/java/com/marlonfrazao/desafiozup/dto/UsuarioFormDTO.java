package com.marlonfrazao.desafiozup.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import com.marlonfrazao.desafiozup.entities.Endereco;
import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.service.validation.UsuarioValid;

@UsuarioValid
public class UsuarioFormDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;
	private String senha;
	
	@NotBlank
	@CPF
	private String cpf;
	private Date dataNascimento;
	
	Set<EnderecoResponseDTO> enderecos = new HashSet<>();
	
	public UsuarioFormDTO() {}
	
	public UsuarioFormDTO(String nome, String email, String senha, String cpf, Date dataNascimento) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}
	
	public UsuarioFormDTO(Usuario entity) {
		nome = entity.getNome();
		email = entity.getEmail();
		senha = entity.getSenha();
		cpf = entity.getCpf();
		dataNascimento = entity.getDataNascimento();
	}
	
	public UsuarioFormDTO(Usuario entity, Set<Endereco> enderecos) {
		this(entity);
		enderecos.forEach(e -> this.enderecos.add(new EnderecoResponseDTO(e)));
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public Set<EnderecoResponseDTO> getEnderecos() {
		return enderecos;
	}
}

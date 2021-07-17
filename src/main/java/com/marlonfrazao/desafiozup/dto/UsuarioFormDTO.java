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
	@Email(message = "Favor entrar com um email válido")
	private String email;
	private String password;
	
	@NotBlank
	@CPF(message = "Favor entrar com um CPF válido")
	private String cpf;
	private Date dataNascimento;
	
	private Set<EnderecoResponseDTO> enderecos = new HashSet<>();
	
	private RoleDTO role;
	
	public UsuarioFormDTO() {}
	
	public UsuarioFormDTO(String nome, String email, String password, String cpf, Date dataNascimento, RoleDTO role) {
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.role = role;
	}
	
	public UsuarioFormDTO(Usuario entity) {
		nome = entity.getNome();
		email = entity.getEmail();
		password = entity.getPassword();
		cpf = entity.getCpf();
		dataNascimento = entity.getDataNascimento();
		role = entity.getRole().convert();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}
}

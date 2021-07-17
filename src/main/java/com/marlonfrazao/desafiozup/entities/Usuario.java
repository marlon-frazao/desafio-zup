package com.marlonfrazao.desafiozup.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.marlonfrazao.desafiozup.dto.UsuarioResponseDTO;


@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@Column(unique = true)
	private String email;
	private String password;
	
	@Column(unique = true)
	private String cpf;
	private Date dataNascimento;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_usuario_endereco", 
				joinColumns = @JoinColumn(name = "usuario_id"), 
				inverseJoinColumns = @JoinColumn(name = "endereco_id"))
	private Set<Endereco> enderecos = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	public Usuario() {}
	
	public Usuario(String nome, String email, String password, String cpf, Date dataNascimento, Role role) {
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.role = role;
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
	public Set<Endereco> getEnderecos() {
		return enderecos;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public UsuarioResponseDTO convert() {
		return new UsuarioResponseDTO(this, enderecos);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(role.getAuthority()));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean hasRole(String roleName) {
		if (role.getAuthority().equals(roleName)) {
			return true;
		}
		
		return false;
	}
}

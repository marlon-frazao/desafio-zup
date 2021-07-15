package com.marlonfrazao.desafiozup.dto;

import java.io.Serializable;

public class EnderecoFormDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String cep;
	private String numero;
	private String complemento;
	
	public EnderecoFormDTO () {}
	
	public EnderecoFormDTO(String cep, String numero, String complemento) {
		this.cep = cep;
		this.numero = numero;
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}

package com.marlonfrazao.desafiozup.tests.factory;

import com.marlonfrazao.desafiozup.dto.EnderecoFormDTO;
import com.marlonfrazao.desafiozup.dto.EnderecoResponseDTO;
import com.marlonfrazao.desafiozup.entities.Endereco;

public class EnderecoFactory {
	
	public static Endereco createEndereco() {
		return new Endereco(null, "Alameda Nino", "123", "Casa", "Universitário", "Corumbá", "MS", "79304-083");
	}

	public static Endereco createEndereco(Long id) {
		Endereco endereco = createEndereco();
		endereco.setId(id);
		return endereco;
	}
	
	public static EnderecoFormDTO createEnderecoFormDTO() {
		return new EnderecoFormDTO("60332400", "1234", "Bloco 4 Apto 12");
	}
	
	public static EnderecoResponseDTO createEnderecoResponseDTO() {
		return new EnderecoResponseDTO(10L, "Alameda dos Flamboyants", "123", "Casa", "Sitio do Recreio de Mansões Bernardo Sayão", "Goiânia", "GO", "74681-230");
	}
}

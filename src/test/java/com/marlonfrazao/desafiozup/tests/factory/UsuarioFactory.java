package com.marlonfrazao.desafiozup.tests.factory;

import java.time.Instant;
import java.util.Date;

import com.marlonfrazao.desafiozup.dto.UsuarioFormDTO;
import com.marlonfrazao.desafiozup.dto.UsuarioResponseDTO;
import com.marlonfrazao.desafiozup.entities.Usuario;

public class UsuarioFactory {

	public static Usuario createUsuario() {
		return new Usuario("Adalberto Rangel", "a.rangel@gmail.com", "123456", "41170544061", Date.from(Instant.parse("1997-05-15T00:00:00Z")));
	}
	
	public static Usuario createUsuario(Long id) {
		Usuario usuario = createUsuario();
		usuario.setId(id);
		return usuario;
	}
	
	public static UsuarioFormDTO createUsuarioFormDTO() {
		return new UsuarioFormDTO("Edson Arantes do Nascimento", "rei.pele@gmail.com", "123456", "16693412084", Date.from(Instant.parse("1997-05-15T00:00:00Z")));
	}
	
	public static UsuarioResponseDTO createUsuarioResponseDTO() {
		return new UsuarioResponseDTO(10L, "Tony Tornado", "tornado@hotmail.com", "28962447002",  Date.from(Instant.parse("1982-05-15T00:00:00Z")));
	}
}

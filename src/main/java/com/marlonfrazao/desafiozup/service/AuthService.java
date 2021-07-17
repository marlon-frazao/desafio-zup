package com.marlonfrazao.desafiozup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.repositories.UsuarioRepository;
import com.marlonfrazao.desafiozup.service.exceptions.ForbiddenException;
import com.marlonfrazao.desafiozup.service.exceptions.UnauthorizedException;

@Service
public class AuthService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional(readOnly = true)
	public Usuario authenticated() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return usuarioRepository.findByEmail(username);
		} catch (Exception e) {
			throw new UnauthorizedException("Invalid user");
		}
	}
	
	public void validateAdmin() {
		Usuario usuario = authenticated();
		if(!usuario.hasRole("ADMIN")) {
			throw new ForbiddenException("Access denied");
		}
	}
}

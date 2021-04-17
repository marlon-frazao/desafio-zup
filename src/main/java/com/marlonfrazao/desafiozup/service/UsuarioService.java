package com.marlonfrazao.desafiozup.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlonfrazao.desafiozup.dto.UsuarioDTO;
import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.repositories.EnderecoRepository;
import com.marlonfrazao.desafiozup.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Transactional
	public UsuarioDTO insert(UsuarioDTO dto) {
		Usuario entity = new Usuario();
		copyDtoToEntity(dto, entity);
		return new UsuarioDTO(repository.save(entity));
	}

	private void copyDtoToEntity(UsuarioDTO dto, Usuario entity) {
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setCpf(dto.getCpf());
		entity.setDataNascimento(dto.getDataNascimento());
		
		entity.getEnderecos().clear();
		
		dto.getEnderecos().forEach(e -> entity.getEnderecos().add(enderecoRepository.getOne(e.getId())));
	}
}

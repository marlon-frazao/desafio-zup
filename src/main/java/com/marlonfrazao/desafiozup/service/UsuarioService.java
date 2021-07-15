package com.marlonfrazao.desafiozup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlonfrazao.desafiozup.dto.UsuarioFormDTO;
import com.marlonfrazao.desafiozup.dto.UsuarioResponseDTO;
import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.repositories.UsuarioRepository;
import com.marlonfrazao.desafiozup.service.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private EnderecoService enderecoService;

	@Transactional(readOnly = true)
	public UsuarioResponseDTO findByEmail(String email) {
		try {
			return repository.findByEmail(email).convert();
		} catch (Exception e) {
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
	}

	@Transactional
	public UsuarioFormDTO insert(UsuarioFormDTO dto) {
		Usuario entity = new Usuario();
		copyDtoToEntity(dto, entity);
		return new UsuarioFormDTO(repository.save(entity));
	}

	private void copyDtoToEntity(UsuarioFormDTO dto, Usuario entity) {
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setCpf(dto.getCpf());
		entity.setDataNascimento(dto.getDataNascimento());

		entity.getEnderecos().clear();

		dto.getEnderecos().forEach(e -> entity.getEnderecos().add(enderecoService.getOne(e.getId())));
	}
}

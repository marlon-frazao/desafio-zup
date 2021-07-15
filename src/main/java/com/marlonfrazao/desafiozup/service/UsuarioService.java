package com.marlonfrazao.desafiozup.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlonfrazao.desafiozup.dto.UsuarioFormDTO;
import com.marlonfrazao.desafiozup.dto.UsuarioResponseDTO;
import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.repositories.UsuarioRepository;
import com.marlonfrazao.desafiozup.service.exceptions.DatabaseException;
import com.marlonfrazao.desafiozup.service.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private EnderecoService enderecoService;
	
	@Transactional(readOnly = true)
	public List<UsuarioResponseDTO> findAll() {
		List<Usuario> list = repository.findAll();
		repository.findUsuarioWithEnderecos(list);
		return list.stream().map(Usuario::convert).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public UsuarioResponseDTO findByEmail(String email) {
		try {
			return repository.findByEmail(email).convert();
		} catch (Exception e) {
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
	}

	@Transactional
	public UsuarioResponseDTO insert(UsuarioFormDTO dto) {
		Usuario entity = new Usuario();
		copyDtoToEntity(dto, entity);
		return new UsuarioResponseDTO(repository.save(entity));
	}
	
	@Transactional
	public UsuarioResponseDTO update(Long id, UsuarioFormDTO dto) {
		try {
			Usuario entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			return repository.save(entity).convert();
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado!");
		}
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID não encontrado!");
		} catch(DataIntegrityViolationException  e) {
			throw new DatabaseException("Violação de integridade do banco de dados!");
		}
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

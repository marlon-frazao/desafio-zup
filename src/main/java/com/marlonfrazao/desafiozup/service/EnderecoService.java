package com.marlonfrazao.desafiozup.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlonfrazao.desafiozup.dto.EnderecoFormDTO;
import com.marlonfrazao.desafiozup.dto.EnderecoResponseDTO;
import com.marlonfrazao.desafiozup.entities.Endereco;
import com.marlonfrazao.desafiozup.repositories.EnderecoRepository;
import com.marlonfrazao.desafiozup.service.exceptions.ResourceNotFoundException;

import feign.FeignException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;
	
	@Autowired
	private CepService cepService;
	
	@Transactional(readOnly = true)
	public List<EnderecoResponseDTO> findAll() {
		return repository.findAll().stream().map(Endereco::convert).collect(Collectors.toList());
	}
	
	@Transactional
	public EnderecoResponseDTO insert(EnderecoFormDTO dto) {
		Endereco entity = new Endereco();
		Endereco endereco = copyDtoToEntity(dto, entity);
		return new EnderecoResponseDTO(repository.save(endereco));
	}
	
	@Transactional
	public EnderecoResponseDTO update(Long id, EnderecoFormDTO dto) {
		try {
			Endereco entity = repository.getOne(id);
			Endereco entityEdit = copyDtoToEntity(dto, entity);
			entityEdit.setId(id);
			return repository.save(entityEdit).convert();
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
		}
	}
	
	private Endereco copyDtoToEntity(EnderecoFormDTO dto, Endereco entity) {
		try {
			entity = cepService.buscaEnderecoPorCep(dto.getCep());
			entity.setNumero(dto.getNumero());
			entity.setComplemento(dto.getComplemento());
			
			return entity;
		} catch(FeignException e) {
			throw new ResourceNotFoundException("CEP não encontrado!");
		}
	}
	
	@Transactional(readOnly = true)
	public Endereco getOne(Long id) {
		return repository.getOne(id);
	}
}

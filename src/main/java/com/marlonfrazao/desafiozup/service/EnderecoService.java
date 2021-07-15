package com.marlonfrazao.desafiozup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlonfrazao.desafiozup.dto.EnderecoResponseDTO;
import com.marlonfrazao.desafiozup.dto.EnderecoFormDTO;
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
	
	@Transactional
	public EnderecoResponseDTO insert(EnderecoFormDTO dto) {
		Endereco entity = new Endereco();
		System.out.println(dto.getCep());
		Endereco endereco = copyDtoToEntity(dto, entity);
		return new EnderecoResponseDTO(repository.save(endereco));
	}

	private Endereco copyDtoToEntity(EnderecoFormDTO dto, Endereco entity) {
		try {
			entity = cepService.buscaEnderecoPorCep(dto.getCep());
			System.out.println(entity.getBairro());
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

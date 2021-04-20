package com.marlonfrazao.desafiozup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlonfrazao.desafiozup.dto.EnderecoDTO;
import com.marlonfrazao.desafiozup.entities.Endereco;
import com.marlonfrazao.desafiozup.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;
	
	@Transactional
	public EnderecoDTO insert(EnderecoDTO dto) {
		Endereco entity = new Endereco();
		copyDtoToEntity(dto, entity);
		return new EnderecoDTO(repository.save(entity));
	}

	private void copyDtoToEntity(EnderecoDTO dto, Endereco entity) {
		entity.setLogradouro(dto.getLogradouro());
		entity.setNumero(dto.getNumero());
		entity.setComplemento(dto.getComplemento());
		entity.setBairro(dto.getBairro());
		entity.setCidade(dto.getLocalidade());
		entity.setEstado(dto.getUf());
		entity.setCep(dto.getCep());
	}
}

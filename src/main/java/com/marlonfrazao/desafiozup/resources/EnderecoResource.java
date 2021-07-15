package com.marlonfrazao.desafiozup.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marlonfrazao.desafiozup.dto.EnderecoResponseDTO;
import com.marlonfrazao.desafiozup.dto.EnderecoFormDTO;
import com.marlonfrazao.desafiozup.service.EnderecoService;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoResource {

	@Autowired
	private EnderecoService service;
	
	@PostMapping
	public ResponseEntity<EnderecoResponseDTO> insert(@RequestBody EnderecoFormDTO dto) {
		EnderecoResponseDTO newDto = new EnderecoResponseDTO();
		newDto = service.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
}

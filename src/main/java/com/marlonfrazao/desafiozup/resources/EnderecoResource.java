package com.marlonfrazao.desafiozup.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marlonfrazao.desafiozup.dto.EnderecoDTO;
import com.marlonfrazao.desafiozup.service.CepService;
import com.marlonfrazao.desafiozup.service.EnderecoService;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoResource {

	@Autowired
	private EnderecoService service;
	
	@Autowired
    private CepService cepService;
	
	@PostMapping
	public ResponseEntity<EnderecoDTO> insert(@RequestBody EnderecoDTO dto) {
		EnderecoDTO newDto = getCep(dto.getCep()).getBody();
		newDto.setNumero(dto.getNumero());
		newDto.setComplemento(dto.getComplemento());
		newDto = service.insert(newDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@GetMapping("/{cep}")
	public ResponseEntity<EnderecoDTO> getCep(@PathVariable String cep) {
		EnderecoDTO endereco = cepService.buscaEnderecoPorCep(cep);
		return ResponseEntity.ok().body(endereco);
	}
}

package com.marlonfrazao.desafiozup.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marlonfrazao.desafiozup.dto.UsuarioFormDTO;
import com.marlonfrazao.desafiozup.dto.UsuarioResponseDTO;
import com.marlonfrazao.desafiozup.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping(value = "/{email}")
	public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable String email){
		return ResponseEntity.ok().body(service.findByEmail(email));
	}
	
	@PostMapping
	public ResponseEntity<UsuarioFormDTO> insert(@Valid @RequestBody UsuarioFormDTO dto) {
		UsuarioFormDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{email}").buildAndExpand(newDto.getEmail()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
}

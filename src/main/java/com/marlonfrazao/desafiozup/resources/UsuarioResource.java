package com.marlonfrazao.desafiozup.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping(value = "/{email}")
	public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable String email){
		return ResponseEntity.ok().body(service.findByEmail(email));
	}
	
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> insert(@Valid @RequestBody UsuarioFormDTO dto) {
		UsuarioResponseDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{email}").buildAndExpand(newDto.getEmail()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{email}")
	public ResponseEntity<UsuarioResponseDTO> update(@PathVariable String email, @RequestBody UsuarioFormDTO dto) {
		return ResponseEntity.ok().body(service.update(email, dto));
	}
	
	@DeleteMapping(value = "/{email}")
	public ResponseEntity<UsuarioResponseDTO> delete(@PathVariable String email) {
		service.delete(email);
		return ResponseEntity.noContent().build();
	}
}

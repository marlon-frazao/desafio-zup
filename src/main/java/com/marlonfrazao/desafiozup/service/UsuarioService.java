package com.marlonfrazao.desafiozup.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlonfrazao.desafiozup.dto.UsuarioFormDTO;
import com.marlonfrazao.desafiozup.dto.UsuarioResponseDTO;
import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.repositories.UsuarioRepository;
import com.marlonfrazao.desafiozup.service.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private RoleService roleService;
	
	@Transactional(readOnly = true)
	public List<UsuarioResponseDTO> findAll() {
		authService.validateAdmin();
		List<Usuario> list = repository.findAll();
		repository.findUsuarioWithEnderecos(list);
		return list.stream().map(Usuario::convert).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public UsuarioResponseDTO findByEmail(String email) {
		authService.validateAdmin();
		try {
			return repository.findByEmail(email).convert();
		} catch (Exception e) {
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
	}

	@Transactional
	public UsuarioResponseDTO insert(UsuarioFormDTO dto) {
		authService.validateAdmin();
		Usuario entity = new Usuario();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		return new UsuarioResponseDTO(repository.save(entity));
	}
	
	@Transactional
	public UsuarioResponseDTO update(String email, UsuarioFormDTO dto) {
		authService.validateAdmin();
		try {
			Usuario entity = repository.findByEmail(email);
			copyDtoToEntity(dto, entity);
			return repository.save(entity).convert();
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado!");
		}
	}
	
	@Transactional
	public void delete(String email) {
		authService.validateAdmin();
		try {
			repository.deleteById(repository.findByEmail(email).getId());
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID não encontrado!");
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID não encontrado!");
		}
	}

	private void copyDtoToEntity(UsuarioFormDTO dto, Usuario entity) {
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setCpf(dto.getCpf());
		entity.setDataNascimento(dto.getDataNascimento());
		entity.setRole(roleService.getOne(dto.getRole().getId()));

		entity.getEnderecos().clear();

		dto.getEnderecos().forEach(e -> entity.getEnderecos().add(enderecoService.getOne(e.getId())));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByEmail(username);
		
		if(usuario == null) {
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("Email not found!");
		}
		
		logger.info("User found: " + username);
		
		return usuario;
	}
}

package com.marlonfrazao.desafiozup.tests.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.marlonfrazao.desafiozup.dto.UsuarioFormDTO;
import com.marlonfrazao.desafiozup.dto.UsuarioResponseDTO;
import com.marlonfrazao.desafiozup.entities.Endereco;
import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.repositories.UsuarioRepository;
import com.marlonfrazao.desafiozup.service.EnderecoService;
import com.marlonfrazao.desafiozup.service.UsuarioService;
import com.marlonfrazao.desafiozup.service.exceptions.ResourceNotFoundException;
import com.marlonfrazao.desafiozup.tests.factory.EnderecoFactory;
import com.marlonfrazao.desafiozup.tests.factory.UsuarioFactory;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceTests {

	@InjectMocks
	private UsuarioService service;
	
	@Mock
	private UsuarioRepository repository;
	
	@Mock
	private EnderecoService enderecoService;
	
	private long existingId;
	private long nonExistingId;
	private String existingEmail;
	private String nonExistingEmail;
	private Usuario usuario;
	private Endereco endereco;
	private List<Usuario> list = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 100000L;
		existingEmail = "teste@teste.com";
		nonExistingEmail = "teste.nonexisting@teste.com";
		usuario = UsuarioFactory.createUsuario(existingId);
		endereco = EnderecoFactory.createEndereco(existingId);
		list.add(usuario);
		
		Mockito.when(enderecoService.getOne(ArgumentMatchers.any())).thenReturn(endereco);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(usuario);
		
		Mockito.when(repository.findByEmail(ArgumentMatchers.any())).thenReturn(usuario);
		
		Mockito.doThrow(EntityNotFoundException.class).when(repository).findByEmail(nonExistingEmail);
		
		Mockito.when(repository.findAll()).thenReturn(list);
		
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
	}
	
	@Test
	public void insertShouldReturnUsuarioResponseDTO() {
		UsuarioFormDTO formDTO = UsuarioFactory.createUsuarioFormDTO();
		
		UsuarioResponseDTO result = service.insert(formDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(UsuarioResponseDTO.class, result.getClass());
	}
	
	@Test
	public void updateShouldReturnUsuarioResponseDTOWhenEmailExists() {
		UsuarioFormDTO formDTO = UsuarioFactory.createUsuarioFormDTO();
		
		UsuarioResponseDTO result = service.update(existingEmail, formDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(UsuarioResponseDTO.class, result.getClass());
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenEmailDoesNotExists() {
		UsuarioFormDTO formDTO = UsuarioFactory.createUsuarioFormDTO();
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingEmail, formDTO);
		});
	}
	
	@Test
	public void findByEmailShouldThrowResourceNotFoundExceptionWhenEmailDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findByEmail(nonExistingEmail);
		});
		
		Mockito.verify(repository, Mockito.times(1)).findByEmail(nonExistingEmail);
	}
	
	@Test
	public void findByEmailShouldReturnProductDTOWhenEmailExists() {
		UsuarioResponseDTO result = service.findByEmail(existingEmail);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(UsuarioResponseDTO.class, result.getClass());
	}
	
	@Test
	public void findAllShouldReturnList() {
		List<UsuarioResponseDTO> result = service.findAll();
		
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Mockito.verify(repository, Mockito.times(1)).findAll();
		Assertions.assertEquals(ArrayList.class, result.getClass());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenEmailDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingEmail);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenEmailExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingEmail);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
}

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

import com.marlonfrazao.desafiozup.dto.EnderecoFormDTO;
import com.marlonfrazao.desafiozup.dto.EnderecoResponseDTO;
import com.marlonfrazao.desafiozup.entities.Endereco;
import com.marlonfrazao.desafiozup.repositories.EnderecoRepository;
import com.marlonfrazao.desafiozup.service.CepService;
import com.marlonfrazao.desafiozup.service.EnderecoService;
import com.marlonfrazao.desafiozup.service.exceptions.ResourceNotFoundException;
import com.marlonfrazao.desafiozup.tests.factory.EnderecoFactory;

@ExtendWith(SpringExtension.class)
public class EnderecoServiceTests {

	@InjectMocks
	private EnderecoService service;
	
	@Mock
	private EnderecoRepository repository;
	
	@Mock
	private CepService cepService;
	
	private long existingId;
	private long nonExistingId;
	private Endereco endereco;
	private List<Endereco> list = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000000L;
		endereco = EnderecoFactory.createEndereco(existingId);
		list.add(endereco);
		
		Mockito.when(cepService.buscaEnderecoPorCep(ArgumentMatchers.any())).thenReturn(endereco);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(endereco);
		
		Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingId);
		
		Mockito.when(repository.findAll()).thenReturn(list);
		
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		
	}
	
	@Test
	public void insertShouldReturnEnderecoResponseDTO() {
		EnderecoFormDTO formDTO = EnderecoFactory.createEnderecoFormDTO();
		
		EnderecoResponseDTO result = service.insert(formDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(EnderecoResponseDTO.class, result.getClass());
	}
	
	@Test
	public void updateShouldReturnEnderecoResponseDTOWhenIdExists() {
		EnderecoFormDTO formDTO = EnderecoFactory.createEnderecoFormDTO();
		
		EnderecoResponseDTO result = service.update(existingId, formDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(EnderecoResponseDTO.class, result.getClass());
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		EnderecoFormDTO formDTO = EnderecoFactory.createEnderecoFormDTO();
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, formDTO);
		});
	}
	
	@Test
	public void findAllShouldReturnList() {
		List<EnderecoResponseDTO> result = service.findAll();
		
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Mockito.verify(repository, Mockito.times(1)).findAll();
		Assertions.assertEquals(ArrayList.class, result.getClass());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
}

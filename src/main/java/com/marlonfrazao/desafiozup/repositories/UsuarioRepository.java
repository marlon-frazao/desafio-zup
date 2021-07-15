package com.marlonfrazao.desafiozup.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.marlonfrazao.desafiozup.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);
	
	Usuario findByCpf(String cpf);
	
	@Query("SELECT obj FROM Usuario obj JOIN FETCH obj.enderecos WHERE obj IN :usuarios")
	List<Usuario> findUsuarioWithEnderecos(List<Usuario> usuarios);
}

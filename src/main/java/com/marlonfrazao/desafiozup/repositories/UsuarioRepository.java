package com.marlonfrazao.desafiozup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marlonfrazao.desafiozup.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}

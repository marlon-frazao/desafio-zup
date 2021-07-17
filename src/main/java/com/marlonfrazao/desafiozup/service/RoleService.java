package com.marlonfrazao.desafiozup.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marlonfrazao.desafiozup.entities.Role;
import com.marlonfrazao.desafiozup.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;
	
	@Transactional(readOnly = true)
	public Role getOne(Long id) {
		return repository.getOne(id);
	}
}

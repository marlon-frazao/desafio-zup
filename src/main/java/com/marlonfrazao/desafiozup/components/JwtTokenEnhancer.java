package com.marlonfrazao.desafiozup.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.marlonfrazao.desafiozup.entities.Usuario;
import com.marlonfrazao.desafiozup.repositories.UsuarioRepository;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario usuario = repository.findByEmail(authentication.getName());
		
		Map<String, Object> map = new HashMap<>();
		map.put("userName", usuario.getNome());
		map.put("userId", usuario.getId());

		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
		token.setAdditionalInformation(map);
		
		return accessToken;
	}

}

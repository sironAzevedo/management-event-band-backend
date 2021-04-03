package com.geb.client.fallback;

import org.springframework.stereotype.Component;

import com.geb.client.UserClient;
import com.geb.model.dto.UserDTO;

@Component
public class UserFallbackFactory implements UserClient {

	@Override
	public UserDTO findByEmail(String bearerToken, String email) {
		return null;
	}

	@Override
	public void addRole(String bearerToken, Long code, String role) {
		
	}

	@Override
	public UserDTO findByKey(String bearerToken, String chave) {
		return null;
	}
}

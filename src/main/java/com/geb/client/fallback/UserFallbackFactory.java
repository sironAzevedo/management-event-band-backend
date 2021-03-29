package com.geb.client.fallback;

import org.springframework.stereotype.Component;

import com.geb.client.UserClient;
import com.geb.model.dto.UserDTO;

@Component
public class UserFallbackFactory implements UserClient {

	@Override
	public UserDTO findByEmail(String email) {
		return null;
	}

}

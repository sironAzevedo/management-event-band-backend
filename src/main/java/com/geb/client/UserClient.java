package com.geb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.geb.client.fallback.UserFallbackFactory;
import com.geb.model.dto.UserDTO;

import feign.Headers;

@Component
@FeignClient(name = "user", url = "${client.user_url}", path = "/v1/users", fallback = UserFallbackFactory.class)
public interface UserClient {

	final String AUTH_TOKEN = "Authorization";

	@GetMapping(value = "/by-email")
	@Headers("Content-Type: application/json")
	UserDTO findByEmail(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String email);
	
	@GetMapping(value = "/by-key")
	@Headers("Content-Type: application/json")
	UserDTO findByKey(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String chave);

	@PutMapping(value = "/role")
	@Headers("Content-Type: application/json")
	void addRole(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam Long code, @RequestParam String role);
	
	@DeleteMapping(value = "/disassociate-perfil")
	@Headers("Content-Type: application/json")
	void disassociatePerfilUser(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam Long code, @RequestParam String role);

}

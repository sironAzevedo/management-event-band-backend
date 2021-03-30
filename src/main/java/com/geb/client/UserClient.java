package com.geb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.geb.client.fallback.UserFallbackFactory;
import com.geb.model.dto.UserDTO;

import feign.Headers;

@Component
@FeignClient(name = "user", url = "${client.user_url}", path = "/v1/users", fallback = UserFallbackFactory.class)
public interface UserClient {

	@GetMapping(value = "/by-email")
	@Headers("Content-Type: application/json")
	UserDTO findByEmail(@RequestParam String email);

}

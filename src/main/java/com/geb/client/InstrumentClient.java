package com.geb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.geb.model.dto.UserDTO;

import feign.Headers;

@Component
@FeignClient(name = "instrument", url = "${client.instrument_url}", path = "/v1/instruments")
public interface InstrumentClient {

	final String AUTH_TOKEN = "Authorization";
	
	@GetMapping(value = "/detail")
	@Headers("Content-Type: application/json")
	UserDTO findByEmail(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam Long code);

}

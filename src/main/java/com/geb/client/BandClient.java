package com.geb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.geb.model.dto.BandDTO;

import feign.Headers;

@Component
@FeignClient(name = "band", url = "${client.band_url}", path = "/v1/bands")
public interface BandClient {

	final String AUTH_TOKEN = "Authorization";
	
	@GetMapping(value = "/detail")
	@Headers("Content-Type: application/json")
	BandDTO find(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam Long code);

}

package com.geb.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.geb.model.dto.VoiceDTO;
import com.geb.service.IVoiceService;

@RestController
@RequestMapping(value = "/v1/voices", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoiceController {
	
	@Autowired
	private IVoiceService service;
	
	@PostMapping
	@ResponseBody
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String create(@Valid @RequestBody VoiceDTO dto) {
		service.create(dto);
		String res = "Cadastro realizado com sucesso";
		return "{\"mensagem\": \"" + res + "\"}";
	}
	
	@DeleteMapping
	@ResponseBody
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@RequestParam(value="code") Long code) {
		service.delete(code);
	}
	
	@ResponseBody
	@GetMapping(value = "/detail")
	@ResponseStatus(value = HttpStatus.OK)
	public VoiceDTO find(
			@RequestParam(value="code") Long code) {
		return service.findByCode(code);
	}
	
	@GetMapping
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<VoiceDTO> findAll() {
		return service.findAll();
	}
}

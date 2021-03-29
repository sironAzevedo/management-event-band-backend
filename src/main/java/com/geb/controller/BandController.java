package com.geb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.geb.model.dto.BandDTO;
import com.geb.service.IBandService;

@RestController
@RequestMapping(value = "/v1/bands", produces = MediaType.APPLICATION_JSON_VALUE)
public class BandController {
	
	@Autowired
	private IBandService service;
	
	@PostMapping
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	public String create(@Valid @RequestBody BandDTO dto) {
		service.create(dto);
		String res = "Cadastro realizado com sucesso";
		return "{\"mensagem\": \"" + res + "\"}";
	}

}

package com.geb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.geb.model.dto.UserDTO;
import com.geb.service.IUserService;

@RestController
@RequestMapping(value = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	private IUserService service;
	
	@PostMapping
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public String create(@Valid @RequestBody UserDTO dto) {
		service.create(dto);
		String res = "Cadastro realizado com sucesso";
		return "{\"mensagem\": \"" + res + "\"}";
	}
	
	@ResponseBody
	@GetMapping(value = "/by-email")
	@ResponseStatus(value = HttpStatus.OK)
	public UserDTO findByEmail(@RequestParam(value="email") String email) {
		return service.findByEmail(email);
	}

}

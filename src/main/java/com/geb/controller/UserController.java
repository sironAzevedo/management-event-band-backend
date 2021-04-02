package com.geb.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@ResponseStatus(value = HttpStatus.CREATED)
	public String create(@Valid @RequestBody UserDTO dto) {
		service.create(dto);
		String res = "Cadastro realizado com sucesso";
		return "{\"mensagem\": \"" + res + "\"}";
	}
	
	@PutMapping
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public String update(@Valid @RequestBody UserDTO dto) {
		service.Update(dto);
		String res = "Cadastro atualizado com sucesso";
		return "{\"mensagem\": \"" + res + "\"}";
	}
	
	@ResponseBody
	@GetMapping(value = "/by-email")
	@ResponseStatus(value = HttpStatus.OK)
	public UserDTO findByEmail(@RequestParam(value="email") String email) {
		return service.findByEmail(email);
	}
	
	@ResponseBody
	@DeleteMapping
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public String delete(@RequestParam(value="code") Long code) {
		service.delete(code);
		String res = "User deleted successfully";
		return "{\"mensagem\": \"" + res + "\"}";
	}
	
	@ResponseBody
	@GetMapping(value = "/instrument")
	@ResponseStatus(value = HttpStatus.OK)
	public void associateInstrument(
			@RequestParam(value="user_code") Long code,
			@RequestParam(value="instruments") List<Long> instruments) {
		service.associateInstrument(code, instruments);
	}
	
	@ResponseBody
	@GetMapping(value = "/voice")
	@ResponseStatus(value = HttpStatus.OK)
	public void associateVoice(
			@RequestParam(value="user_code") Long code,
			@RequestParam(value="voices") List<Long> voices) {
		service.associateVoice(code, voices);
	}

}

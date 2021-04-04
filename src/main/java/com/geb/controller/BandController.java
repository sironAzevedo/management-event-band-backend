package com.geb.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.geb.model.dto.BandDTO;
import com.geb.model.dto.MembersDTO;
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
	
	@DeleteMapping
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@RequestParam(value="code") Long code) {
		service.delete(code);
	}
	
	@ResponseBody
	@GetMapping(value = "/detail")
	@ResponseStatus(value = HttpStatus.OK)
	public BandDTO find(@RequestParam(value="code") Long code) {
		return service.find(code);
	}
	
	@ResponseBody
	@GetMapping(value = "/associate-member")
	@ResponseStatus(value = HttpStatus.OK)
	public void associateMembers(
			@RequestParam(value="band_code") Long code,
			@RequestParam(value="email_member") String email,
			@RequestParam(value="leader", defaultValue = "false") Boolean leader,
			@RequestParam(value="instrument_code", required = false) Long instrumentCode,
			@RequestParam(value="voice_code", required = false) Long voiceCode) {
		service.associateMembers(code, email, leader, instrumentCode, voiceCode);
	}
	
	@ResponseBody
	@DeleteMapping(value = "/disassociate-member")
	@ResponseStatus(value = HttpStatus.OK)
	public void disassociateMembers(
			@RequestParam(value="band_code") Long code,
			@RequestParam(value="email_member") String email) {
		service.disassociateMembers(code, email);
	}
	
	@ResponseBody
	@GetMapping(value = "/by-user")
	@ResponseStatus(value = HttpStatus.OK)
	public List<BandDTO> findAssociatedBandsByUser(
			@RequestParam(value="email_member") String email) {
		return service.findAssociatedBandsByUser(email);
	}
	
	@ResponseBody
	@GetMapping(value = "/members")
	@ResponseStatus(value = HttpStatus.OK)
	public List<MembersDTO> associateMembers(
			@RequestParam(value="band_code") Long code) {
		return service.findMembers(code);
	}
}

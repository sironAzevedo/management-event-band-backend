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

import com.geb.model.dto.EventDTO;
import com.geb.service.IEventService;

@RestController
@RequestMapping(value = "/v1/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {
	
	@Autowired
	private IEventService service;
	
	@PostMapping
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	public String create(@Valid @RequestBody EventDTO dto, @RequestParam(value="band") Long bandId) {
		service.create(dto, bandId);
		String res = "Cadastro realizado com sucesso";
		return "{\"mensagem\": \"" + res + "\"}";
	}
	
	@ResponseBody
	@DeleteMapping
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@RequestParam(value="code") Long code) {
		service.delete(code);
	}
	
	@ResponseBody
	@GetMapping(value = "/detail")
	@ResponseStatus(value = HttpStatus.OK)
	public EventDTO find(
			@RequestParam(value="code") Long code) {
		return service.findByCode(code);
	}
	
	@ResponseBody
	@GetMapping(value = "/by-band")
	@ResponseStatus(value = HttpStatus.OK)
	public List<EventDTO> findByBand(@RequestParam(value="band") Long bandId) {
		return service.findByBand(bandId);
	}
}

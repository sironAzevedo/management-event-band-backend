package com.geb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geb.client.BandClient;
import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.handler.exception.NotFoundException;
import com.geb.mapper.EventMapper;
import com.geb.model.Band;
import com.geb.model.Event;
import com.geb.model.dto.BandDTO;
import com.geb.model.dto.EventDTO;
import com.geb.repository.IEventRepository;
import com.geb.service.IEventService;

@Service
public class EventServiceImpl implements IEventService {
	
	@Autowired
	private IEventRepository repository;
	
	@Autowired
	private BandClient bandClient;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public void create(EventDTO dto, Long bandId) {
		Band band = this.addBand(getToken(), bandId);
		Event event = EventMapper.INSTANCE.toEntity(dto);
		event.setBand(band);
		repository.save(event);
	}

	@Override
	public void delete(Long code) {
		this.findByBand(code);
		repository.deleteById(code);
	}

	@Override
	public EventDTO findByCode(Long code) {
		return repository.findById(code).map(EventMapper.INSTANCE::toDTO).orElseThrow(()-> new EmptyResultDataAccessException("Event not found"));
	}

	@Override
	public List<EventDTO> findByBand(Long bandId) {
		/*
		  	Validação:
		 	O Cliente só pode visualizar os eventos das bandas criada ou associada a ele
		 */
		
		
		return repository.findByBandCodigo(bandId).stream().map(EventMapper.INSTANCE::toDTO).collect(Collectors.toList());
	}
	
	private Band addBand(String token, Long bandId) {
		try {
			BandDTO res = bandClient.find(token, bandId);
			return Band.builder().codigo(res.getCodigo()).build();
		} catch (Exception e) {
			throw new NotFoundException("Band not foud");
		}
	}
	
	private String getToken() {
		return request.getHeader("Authorization");
	}

}

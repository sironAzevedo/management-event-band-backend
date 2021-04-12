package com.geb.service;

import java.util.List;

import com.geb.model.dto.EventDTO;

public interface IEventService {
	
	void create(EventDTO dto, Long bandId);

	void delete(Long code);	 

	EventDTO findByCode(Long code);
	
	List<EventDTO> findByBand(Long bandId);
}

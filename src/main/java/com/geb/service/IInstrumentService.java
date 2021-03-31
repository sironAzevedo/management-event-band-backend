package com.geb.service;

import java.util.List;

import com.geb.model.dto.InstrumentDTO;

public interface IInstrumentService {
	
	void create(InstrumentDTO dto);
	
	void delete(Long code);

	List<InstrumentDTO> findAll();

	InstrumentDTO findByCode(Long code);

}

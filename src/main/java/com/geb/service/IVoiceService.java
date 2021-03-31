package com.geb.service;

import java.util.List;

import com.geb.model.dto.VoiceDTO;

public interface IVoiceService {
	
	void create(VoiceDTO dto);
	
	void delete(Long code);

	List<VoiceDTO> findAll();

	VoiceDTO findByCode(Long code);
}

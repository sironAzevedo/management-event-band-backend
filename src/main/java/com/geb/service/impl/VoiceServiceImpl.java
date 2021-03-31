package com.geb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.mapper.VoiceMapper;
import com.geb.model.dto.VoiceDTO;
import com.geb.repository.IVoiceRepository;
import com.geb.service.IVoiceService;

@Service
public class VoiceServiceImpl implements IVoiceService {
	
	@Autowired
	private IVoiceRepository repository;
	
	@Override
	public void create(VoiceDTO dto) {
		repository.save(VoiceMapper.INSTANCE.toEntity(dto));
	}

	@Override
	public void delete(Long code) {
		this.findByCode(code);
		repository.deleteById(code);
	}

	@Override
	public List<VoiceDTO> findAll() {
		return repository.findAll().stream().map(VoiceMapper.INSTANCE::toDTO).collect(Collectors.toList());
	}

	@Override
	public VoiceDTO findByCode(Long code) {
		return repository.findById(code).map(VoiceMapper.INSTANCE::toDTO).orElseThrow(()-> new EmptyResultDataAccessException("Voice not found"));
	}
}

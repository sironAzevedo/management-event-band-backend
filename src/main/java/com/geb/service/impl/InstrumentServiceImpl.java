package com.geb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.mapper.InstrumentMapper;
import com.geb.model.Instrument;
import com.geb.model.InstrumentGroup;
import com.geb.model.dto.InstrumentDTO;
import com.geb.repository.IInstrumentGroupRepository;
import com.geb.repository.IInstrumentRepository;
import com.geb.service.IInstrumentService;

@Service
public class InstrumentServiceImpl implements IInstrumentService {
	
	@Autowired
	private IInstrumentRepository repository;
	
	@Autowired
	private IInstrumentGroupRepository group;
	
	@Override
	public void create(InstrumentDTO dto, Long groupId) {
		Instrument entity = InstrumentMapper.INSTANCE.toEntity(dto);
		InstrumentGroup gp = group.findById(groupId).orElseThrow(()-> new EmptyResultDataAccessException("Grup not found"));
		entity.setGroup(gp);
		repository.save(entity);
	}

	@Override
	public void delete(Long code) {
		this.findByCode(code);
		repository.deleteById(code);
	}

	@Override
	public List<InstrumentDTO> findAll() {
		return repository.findAll().stream().map(InstrumentMapper.INSTANCE::toDTO).collect(Collectors.toList());
	}

	@Override
	public InstrumentDTO findByCode(Long code) {
		return repository.findById(code).map(InstrumentMapper.INSTANCE::toDTO).orElseThrow(()-> new EmptyResultDataAccessException("Instrument not found"));
	}
}

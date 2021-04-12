package com.geb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.geb.model.Event;
import com.geb.model.dto.EventDTO;

@Mapper
public interface EventMapper {

	EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

	Event toEntity(EventDTO dto);

	EventDTO toDTO(Event entity);

}

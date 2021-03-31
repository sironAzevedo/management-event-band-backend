package com.geb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.geb.model.Voice;
import com.geb.model.dto.VoiceDTO;

@Mapper
public interface VoiceMapper {
	
	VoiceMapper INSTANCE = Mappers.getMapper(VoiceMapper.class);
	
	@Mappings({
	      @Mapping(target="name", source="dto.name")
	    })
	Voice toEntity(VoiceDTO dto);
	
	@Mappings({
	      @Mapping(target="code", source="entity.code"),
	      @Mapping(target="name", source="entity.name")
	    })
	VoiceDTO toDTO(Voice entity);

}

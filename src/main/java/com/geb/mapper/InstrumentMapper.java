package com.geb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.geb.model.Instrument;
import com.geb.model.dto.InstrumentDTO;

@Mapper
public interface InstrumentMapper {
	
	InstrumentMapper INSTANCE = Mappers.getMapper(InstrumentMapper.class);
	
	@Mappings({
	      @Mapping(target="name", source="dto.name")
	    })
	Instrument toEntity(InstrumentDTO dto);
	
	@Mappings({
	      @Mapping(target="codigo", source="entity.codigo"),
	      @Mapping(target="name", source="entity.name")
	    })
	InstrumentDTO toDTO(Instrument entity);

}

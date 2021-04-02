package com.geb.mapper;

import org.mapstruct.Mapper;

import com.geb.model.Band;
import com.geb.model.dto.BandDTO;
import com.geb.model.enums.LeaderEnum;

@Mapper(componentModel = "spring")
public abstract class BandMapper {
	
	public Band toEntity(BandDTO dto) {
		return Band
				.builder()
				.name(dto.getName())
				.build();
	}
	
	public BandDTO toDTO(Band band) {
		
		String leader = band.getInfo().stream()
				.filter(l -> LeaderEnum.S.equals(l.getLeader()))
				.findAny()
				.map(m -> m.getCodigo().getUser().getName())
				.orElse(null);
		
		return BandDTO
				.builder()
				.codigo(band.getCodigo())
				.name(band.getName())
				.memberLeader(leader)
				.build();
	}

}

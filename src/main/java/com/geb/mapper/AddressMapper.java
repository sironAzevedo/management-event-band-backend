package com.geb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.geb.model.Address;
import com.geb.model.dto.AddressDTO;

@Mapper
public interface AddressMapper {
	
	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
	
	Address toEntity(AddressDTO dto);
	
	AddressDTO toDTO(Address entity);

}

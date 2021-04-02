package com.geb.model.enums.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.geb.model.enums.TypePersonEnum;

@Converter(autoApply = true)
public class TypePersonConvert implements AttributeConverter<TypePersonEnum, String> {

	@Override
	public String convertToDatabaseColumn(TypePersonEnum attribute) {
		if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
	}

	@Override
	public TypePersonEnum convertToEntityAttribute(String code) {
		if (code == null) {
            return null;
        }
		
		return TypePersonEnum.from(code);
	}

}

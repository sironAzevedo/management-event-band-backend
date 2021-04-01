package com.geb.model.enums.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.geb.model.enums.PerfilEnum;

@Converter(autoApply = true)
public class PerfilConvert implements AttributeConverter<PerfilEnum, String> {

	@Override
	public String convertToDatabaseColumn(PerfilEnum attribute) {
		if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
	}

	@Override
	public PerfilEnum convertToEntityAttribute(String code) {
		if (code == null) {
            return null;
        }
		
		return PerfilEnum.from(code);
	}

}

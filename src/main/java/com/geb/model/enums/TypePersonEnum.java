package com.geb.model.enums;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum TypePersonEnum {
	PF("PF"),
	PJ("PJ");
	
private String codigo;
	
	private TypePersonEnum(String codigo) {
		this.codigo = codigo;
	}
	
	public static TypePersonEnum from(String code) {
		return Stream.of(TypePersonEnum.values())
        .filter(c -> c.getCodigo().equals(code))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
	}
}

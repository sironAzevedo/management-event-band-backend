package com.geb.model.enums;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum PerfilEnum {
	ADMIN("ROLE_ADMIN"), 
	USER("ROLE_USER");
	
	private String codigo;
	
	private PerfilEnum(String codigo) {
		this.codigo = codigo;
	}
	
	public static PerfilEnum from(String code) {
		return Stream.of(PerfilEnum.values())
        .filter(c -> c.getCodigo().equals(code))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
	}
	
	@Override
	public String toString() {
		return codigo;
	}
}

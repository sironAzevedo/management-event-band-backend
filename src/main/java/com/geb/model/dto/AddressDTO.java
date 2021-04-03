package com.geb.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String street;
    private String district;
    private String city;
    private String state;
    private String country;
    private String cep;
}

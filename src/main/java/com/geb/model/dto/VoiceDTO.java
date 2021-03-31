package com.geb.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VoiceDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Long codigo;
    private String name;

}

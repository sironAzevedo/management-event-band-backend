package com.geb.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geb.model.enums.LeaderEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MembersDTO {
	
	private String name;
    private String email;
	private LeaderEnum leader;
	private String intrumentName;
	private String voiceName;

}

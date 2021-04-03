package com.geb.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BandDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long codigo;
    private String name;
    private String chavePj;
    
    @Email
    private String memberLeader;
    private List<UserDTO> members;

}

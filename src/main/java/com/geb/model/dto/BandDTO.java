package com.geb.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BandDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long codigo;
    private String name;
    
    @Email
    private String memberLeader;
    private List<UserDTO> members;

}

package com.geb.service;

import java.util.List;

import com.geb.model.dto.UserDTO;

public interface IUserService {

    void create(UserDTO user);

    void Update(UserDTO user);

    void delete(Long code);

    UserDTO findByEmail(String email);
    
    void associateInstrument(Long userCode, List<Long> instruments);
    
    void associateVoice(Long userCode, List<Long> voices);
}

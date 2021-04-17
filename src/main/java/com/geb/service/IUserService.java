package com.geb.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.geb.model.dto.UserDTO;

public interface IUserService {

    void create(UserDTO user);

    void update(UserDTO user);
    
    void addRole(Long code, String role);

    void delete(Long code);

    UserDTO findByEmail(String email);
    
    UserDTO findByChave(String chave);
    
    void associateInstrument(Long userCode, List<Long> instruments);
    
    void associateVoice(Long userCode, List<Long> voices);
    
    void disassociatePerfilUser(Long user, String role);
    
    List<UserDTO> findByUserLike(String value);

	void sendNewPassword(String email);

	void photoProfile(MultipartFile file);
}

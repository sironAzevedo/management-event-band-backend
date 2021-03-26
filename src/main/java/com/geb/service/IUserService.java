package com.geb.service;

import com.geb.model.dto.UserDTO;

public interface IUserService {

    void create(UserDTO user);

    void Update(UserDTO user);

    void delete(Long code);

    UserDTO findByEmail(String email);
}

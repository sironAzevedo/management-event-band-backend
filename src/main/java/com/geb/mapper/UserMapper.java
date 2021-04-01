package com.geb.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.geb.model.User;
import com.geb.model.dto.UserDTO;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private BCryptPasswordEncoder pe;

    public User toEntity(UserDTO dto) {

        String password = pe.encode(dto.getPassword());
        return User
                .builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .confirmEmail(dto.getConfirmEmail())
                .phone(dto.getPhone())
                .password(password)
                .confirmPassword(password)
                .typeUser(dto.getTypeUser())
                .build();
    }
    
    public UserDTO toDTO(User user) {
    	return UserDTO
                .builder()
                .codigo(user.getCodigo())
                .name(user.getName())
                .email(user.getEmail())
                .confirmEmail(user.getConfirmEmail())
                .phone(user.getPhone())
                .password(user.getPassword())
                .confirmPassword(user.getConfirmPassword())
                .typeUser(user.getTypeUser())
                .build();
    }
}

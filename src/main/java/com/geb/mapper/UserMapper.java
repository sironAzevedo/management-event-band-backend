package com.geb.mapper;

import com.geb.model.User;
import com.geb.model.dto.UserDTO;
import com.geb.model.enums.PerfilEnum;
import com.geb.repository.IRoleRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private IRoleRepository roleRepository;

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
                .roles(roleRepository.findByPerfil(PerfilEnum.ROLE_USER))
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

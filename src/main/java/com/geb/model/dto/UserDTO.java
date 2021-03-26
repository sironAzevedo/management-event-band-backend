package com.geb.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;

import com.geb.model.enums.TypePersonEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private String name;
    @Email private String email;
    @Email private String confirmEmail;
    private String phone;
    private String password;
    private String confirmPassword;
    private TypePersonEnum typeUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

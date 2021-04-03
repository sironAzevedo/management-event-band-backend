package com.geb.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;

import com.geb.model.enums.TypePersonEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long codigo;
    private String name;
    private String socialReason;
    private LocalDate birthDate;
    @Email private String email;
    @Email private String confirmEmail;
    private String phone;
    private String password;
    private String confirmPassword;
    private TypePersonEnum typeUser;
    private String chavePj;
    private AddressDTO address;
    @Default private List<String> roles = new ArrayList<>();
}

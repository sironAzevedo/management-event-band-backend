package com.geb.mapper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.geb.model.Instrument;
import com.geb.model.User;
import com.geb.model.Voice;
import com.geb.model.dto.InstrumentDTO;
import com.geb.model.dto.UserDTO;
import com.geb.model.dto.VoiceDTO;
import com.geb.model.enums.TypePersonEnum;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private BCryptPasswordEncoder pe;

    public User toEntity(UserDTO dto) {

        String password = pe.encode(dto.getPassword());
        return User
                .builder()
                .codigo(dto.getCodigo())
                .name(dto.getName())
                .email(dto.getEmail())
                .socialReason(dto.getSocialReason())
                .birthDate(dto.getBirthDate())
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
                .chavePj(getChave(user))
                .instruments(Objects.nonNull(user.getInstruments()) ? instruments(user.getInstruments()) : null)
                .voices(Objects.nonNull(user.getVoices()) ? voices(user.getVoices()) : null)
                .build();
    }
    
    public UserDTO toListMembers(User user) {
    	return UserDTO
                .builder()
                .codigo(user.getCodigo())
                .name(user.getName())
                .email(user.getEmail())
                .instruments(Objects.nonNull(user.getInstruments()) ? instruments(user.getInstruments()) : null)
                .voices(Objects.nonNull(user.getVoices()) ? voices(user.getVoices()) : null)
                .build();
    }
    
    private String getChave(User user) {
    	if(TypePersonEnum.PF.equals(user.getTypeUser())) {
    		if(Objects.nonNull(user.getAssociated())) {
    			return user.getAssociated().getChave();
    		} 
    	} else if((Objects.nonNull(user.getChave()))){
			return user.getChave().getChave();
		}
    	
    	return null;
    }
    
    private Set<InstrumentDTO> instruments(Set<Instrument> instruments) {
    	return instruments.stream().map(InstrumentMapper.INSTANCE::toDTO).collect(Collectors.toSet());
    }
    
    private Set<VoiceDTO> voices(Set<Voice> voices) {
    	return voices.stream().map(VoiceMapper.INSTANCE::toDTO).collect(Collectors.toSet());
    }
}

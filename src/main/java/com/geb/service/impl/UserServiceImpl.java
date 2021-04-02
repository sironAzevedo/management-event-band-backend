package com.geb.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.geb.client.InstrumentClient;
import com.geb.client.VoiceClient;
import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.handler.exception.UserException;
import com.geb.mapper.UserMapper;
import com.geb.model.Instrument;
import com.geb.model.Role;
import com.geb.model.User;
import com.geb.model.Voice;
import com.geb.model.dto.InstrumentDTO;
import com.geb.model.dto.UserDTO;
import com.geb.model.dto.VoiceDTO;
import com.geb.model.enums.PerfilEnum;
import com.geb.repository.IRoleRepository;
import com.geb.repository.IUserPerository;
import com.geb.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUserPerository repo;
    
    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private UserMapper mapper;
    
    @Autowired
    private InstrumentClient instrumentClient;
    
    @Autowired
    private VoiceClient voiceClien;
    
    @Autowired
	private HttpServletRequest request;

    @Override
    public void create(UserDTO user) {
    	
    	if (repo.existsByEmail(user.getEmail())) {
			throw new UserException("User exist", HttpStatus.BAD_REQUEST.value());
		}
    	
    	User entity = mapper.toEntity(user);
    	Set<Role> roles = new HashSet<>();
    	if(user.getRoles().isEmpty()) {
    		switch (user.getTypeUser()) {
			case PF:
				roles.add(roleRepository.findByPerfil(PerfilEnum.USER));
				break;
			case PJ:
				roles.add(roleRepository.findByPerfil(PerfilEnum.MODERATOR));
				break;
			default:
				roles.add(roleRepository.findByPerfil(PerfilEnum.USER));
			}
    	} else {
    		user.getRoles().forEach(role -> {
    			roles.add(roleRepository.findByPerfil(PerfilEnum.from(role)));
    		});
    	}
    	
    	entity.setRoles(roles);
    	repo.save(entity);
    }

    @Override
    public void Update(UserDTO user) {
    	if (repo.existsByEmail(user.getEmail())) {
			throw new UserException("User not found", HttpStatus.NOT_FOUND.value());
		}
    	
    	AuthService.authenticated(user.getEmail());
    	
    	User entity = mapper.toEntity(user);
    	repo.save(entity);
    }

    @Override
    public void delete(Long code) {
    	Optional<User> user = repo.findById(code);
    	if(!user.isPresent()) {
    		throw new UserException("User not found", HttpStatus.NOT_FOUND.value());
    	}
    	AuthService.authenticated(user.get().getEmail());
    	repo.deleteById(code);
    }

    @Override
    public UserDTO findByEmail(String email) {
    	return repo.findByEmail(email).map(mapper::toDTO)
    			.orElseThrow(()-> new UserException("User not found", HttpStatus.NOT_FOUND.value()));
    }
    
    @Override
	public UserDetails loadUserByUsername(String email) {
		return repo.findByEmail(email)
    			.orElseThrow(()-> new UserException("User not found", HttpStatus.NOT_FOUND.value()));
	}

	@Override
	public void associateInstrument(Long userCode, List<Long> instruments) {
		User user = repo.findById(userCode).orElseThrow(()-> new UserException("User not found", HttpStatus.NOT_FOUND.value()));
			Set<Instrument> list = new HashSet<>();
			String token = request.getHeader("Authorization");
			instruments.forEach(i -> {
				try {
					InstrumentDTO res = instrumentClient.find(token, i);
					Instrument instrument = Instrument
							.builder()
							.codigo(res.getCodigo())
							.name(res.getName())
							.build();
					list.add(instrument);					
				} catch (Exception e) {
					throw new EmptyResultDataAccessException("Instrument not foud");
				}
			});
			
			user.setInstruments(list);
			repo.save(user);
	} 
	
	@Override
	public void associateVoice(Long userCode, List<Long> voices) {
		User user = repo.findById(userCode).orElseThrow(()-> new UserException("User not found", HttpStatus.NOT_FOUND.value()));
			Set<Voice> list = new HashSet<>();
			String token = request.getHeader("Authorization");
			voices.forEach(i -> {
				try {
					VoiceDTO res = voiceClien.find(token, i);
					Voice voice = Voice
							.builder()
							.codigo(res.getCodigo())
							.name(res.getName())
							.build();
					list.add(voice);					
				} catch (Exception e) {
					throw new EmptyResultDataAccessException("Voice not foud");
				}
			});
			
			user.setVoices(list);
			repo.save(user);
	} 
}

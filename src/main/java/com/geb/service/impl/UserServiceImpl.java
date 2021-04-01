package com.geb.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.geb.client.InstrumentClient;
import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.handler.exception.UserException;
import com.geb.mapper.UserMapper;
import com.geb.model.Instrument;
import com.geb.model.User;
import com.geb.model.Voice;
import com.geb.model.dto.InstrumentDTO;
import com.geb.model.dto.UserDTO;
import com.geb.repository.IUserPerository;
import com.geb.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUserPerository repo;
    
    @Autowired
    private UserMapper mapper;
    
    @Autowired
    private InstrumentClient client;
    
    @Autowired
	private HttpServletRequest request;

    @Override
    public void create(UserDTO user) {
    	
    	if (repo.existsByEmail(user.getEmail())) {
			throw new UserException("User exist", HttpStatus.BAD_REQUEST.value());
		}
    	
    	User entity = mapper.toEntity(user);
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
    	
    	if (!repo.findById(code).isPresent()) {
			throw new UserException("User not found", HttpStatus.NOT_FOUND.value());
		}
    	
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
					InstrumentDTO res = client.find(token, i);
					list.add(Instrument.builder().codigo(res.getCodigo()).build());					
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
					InstrumentDTO res = client.find(token, i);
					list.add(Voice.builder().codigo(res.getCodigo()).build());					
				} catch (Exception e) {
					throw new EmptyResultDataAccessException("Instrument not foud");
				}
			});
			
			user.setVoices(list);
			repo.save(user);
	} 
}

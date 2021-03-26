package com.geb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geb.handler.exception.UserException;
import com.geb.mapper.UserMapper;
import com.geb.model.User;
import com.geb.model.dto.UserDTO;
import com.geb.repository.IUserPerository;
import com.geb.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserPerository repo;
    
    @Autowired
    private UserMapper mapper;

    @Override
    public void create(UserDTO user) {
    	
    	if (repo.existsByEmail(user.getEmail())) {
			throw new UserException("User not found");
		}
    	
    	User entity = mapper.toEntity(user);
    	repo.save(entity);
    }

    @Override
    public void Update(UserDTO user) {
    	if (repo.existsByEmail(user.getEmail())) {
			throw new UserException("User not found");
		}
    	
    	User entity = mapper.toEntity(user);
    	repo.save(entity);
    }

    @Override
    public void delete(Long code) {
    	
    	if (!repo.findById(code).isPresent()) {
			throw new UserException("User not found");
		}
    	
    	repo.deleteById(code);
    }

    @Override
    public UserDTO findByEmail(String email) {
        return repo.findByEmail(email).map(mapper::toDTO).orElseThrow(()-> new UserException("User not found"));
    }
}

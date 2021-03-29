package com.geb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.geb.client.UserClient;
import com.geb.handler.exception.UserException;
import com.geb.mapper.BandMapper;
import com.geb.model.Band;
import com.geb.model.User;
import com.geb.model.UserBand;
import com.geb.model.dto.BandDTO;
import com.geb.model.dto.UserDTO;
import com.geb.model.enums.LeaderEnum;
import com.geb.repository.IBandPerository;
import com.geb.repository.IUserBandRepository;
import com.geb.service.IBandService;

@Service
public class BandServiceImpl implements IBandService {
	
	@Autowired
	private IBandPerository repository;
	
	@Autowired
	private IUserBandRepository userBandRepository;
	
	@Autowired
	private BandMapper mapper;
	
	@Autowired
	private UserClient userClient;

	@Override
	public void create(BandDTO dto) {
		UserDTO user = this.getUser(dto.getMemberLeader());
		Band band = repository.save(mapper.toEntity(dto));
				
		UserBand userBand = UserBand
				.builder()
				.user(User.builder().codigo(user.getCodigo()).build())
				.band(band)
				.leader(LeaderEnum.S)
				.build();
		
		
		userBandRepository.save(userBand);
		

	}

	@Override
	public void Update(BandDTO band) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long code) {
		// TODO Auto-generated method stub

	}

	@Override
	public BandDTO find(Long code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void associateMembers(Long codeBand, String emailMember, Boolean leader) {
		// TODO Auto-generated method stub

	}
	
	private UserDTO getUser(String user) {
		return Optional.ofNullable(userClient.findByEmail(user)).orElseThrow(() -> new UserException("User not found", HttpStatus.BAD_REQUEST.value()));
	}

}

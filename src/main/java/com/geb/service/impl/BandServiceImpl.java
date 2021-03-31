package com.geb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geb.client.UserClient;
import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.handler.exception.UserException;
import com.geb.mapper.BandMapper;
import com.geb.model.Band;
import com.geb.model.User;
import com.geb.model.BandInfo;
import com.geb.model.dto.BandDTO;
import com.geb.model.dto.MembersDTO;
import com.geb.model.dto.UserDTO;
import com.geb.model.enums.LeaderEnum;
import com.geb.repository.IBandPerository;
import com.geb.repository.IBandInfoRepository;
import com.geb.service.IBandService;

@Service
@Transactional
public class BandServiceImpl implements IBandService {
	
	@Autowired
	private IBandPerository repository;
	
	@Autowired
	private IBandInfoRepository userBandRepository;
	
	@Autowired
	private BandMapper mapper;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public void create(BandDTO dto) {
		UserDTO user = this.getUser(dto.getMemberLeader());
		Band band = repository.save(mapper.toEntity(dto));
				
		BandInfo userBand = BandInfo
				.builder()
				.user(User.builder().codigo(user.getCodigo()).build())
				.band(band)
				.leader(LeaderEnum.S)
				.build();
		
		
		userBandRepository.save(userBand);
	}

	@Override
	public void Update(BandDTO band) {
		find(band.getCodigo());
		repository.save(mapper.toEntity(band));
	}

	@Override
	public void delete(Long code) {
		find(code);
		repository.deleteById(code);

	}

	@Override
	public BandDTO find(Long code) {
		return repository.findById(code).map(mapper::toDTO).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
	}

	@Override
	public void associateMembers(Long codeBand, String emailMember, Boolean leader) {
		UserDTO user = this.getUser(emailMember);
		userBandRepository.findByUserCodigo(user.getCodigo()).ifPresent(r -> {
			throw new UserException("Este usuario estar associado a estar banda", HttpStatus.BAD_REQUEST.value());
		});
		
		Band band = repository.findById(codeBand).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
		BandInfo userBand = BandInfo
				.builder()
				.user(User.builder().codigo(user.getCodigo()).build())
				.band(band)
				.leader(leader? LeaderEnum.S: LeaderEnum.N)
				.build();
		userBandRepository.save(userBand);

	} 
	
	@Override
	public void disassociateMembers(Long codeBand, String emailMember) {
		find(codeBand);
		UserDTO user = this.getUser(emailMember);
		userBandRepository.disassociateMembers(codeBand, user.getCodigo());
	}
	
	@Override
	public List<MembersDTO> findMembers(Long codeBand) {
		List<MembersDTO> members = new ArrayList<>();
		Band band = repository.findById(codeBand).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
		
		List<BandInfo> result = band.getMembers();
		for (BandInfo ub : result) {
			MembersDTO member = MembersDTO
					.builder()
					.codigo(ub.getUser().getCodigo())
					.name(ub.getUser().getName())
					.email(ub.getUser().getEmail())
					.leader(ub.getLeader())
					.build();
			members.add(member);
		}
		return members;
	}
	
	@Override
	public List<BandDTO> findAssociatedBandsByUser(String email) {
		UserDTO user = this.getUser(email);
		AuthService.authenticated(user.getEmail());
		
		List<BandDTO> result = new ArrayList<>();
		List<BandInfo> bands = userBandRepository.findAssociatedBandsByUser(user.getCodigo());
		for (BandInfo ub : bands) {
			BandDTO band = mapper.toDTO(ub.getBand());
			result.add(band);
		}
		
		return result;
	}
	
	private UserDTO getUser(String user) {
		String token = request.getHeader("Authorization");
		return Optional.ofNullable(userClient.findByEmail(token, user)).orElseThrow(() -> new UserException("User not found", HttpStatus.BAD_REQUEST.value()));
	}
}

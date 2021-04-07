package com.geb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geb.client.InstrumentClient;
import com.geb.client.UserClient;
import com.geb.client.VoiceClient;
import com.geb.handler.exception.BandException;
import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.handler.exception.NotFoundException;
import com.geb.handler.exception.UserException;
import com.geb.mapper.BandMapper;
import com.geb.model.Band;
import com.geb.model.BandInfo;
import com.geb.model.BandInfoPk;
import com.geb.model.Instrument;
import com.geb.model.PjAssociatedBand;
import com.geb.model.User;
import com.geb.model.Voice;
import com.geb.model.dto.BandDTO;
import com.geb.model.dto.InstrumentDTO;
import com.geb.model.dto.MembersDTO;
import com.geb.model.dto.UserDTO;
import com.geb.model.dto.VoiceDTO;
import com.geb.model.enums.LeaderEnum;
import com.geb.model.enums.PerfilEnum;
import com.geb.repository.IBandInfoRepository;
import com.geb.repository.IBandPerository;
import com.geb.service.IBandService;

@Service
@Transactional
public class BandServiceImpl implements IBandService {
	
	@Autowired
	private IBandPerository repository;
	
	@Autowired
	private IBandInfoRepository bandInfoRepository;
	
	@Autowired
	private BandMapper mapper;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
    private InstrumentClient instrumentClient;
    
    @Autowired
    private VoiceClient voiceClient;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public void create(BandDTO dto) {
		AuthService.authAdminOrModerator();
		Band band = mapper.toEntity(dto);
		if(StringUtils.isNotBlank(dto.getChavePj())) {
			if(Objects.nonNull(userClient.findByKey(getToken(), dto.getChavePj()))) {
				PjAssociatedBand associated = PjAssociatedBand
						.builder()
						.chave(dto.getChavePj())
						.band(band)
						.build();
				
				band.setAssociated(associated);
			}
		}
		repository.save(band);
	}

	@Override
	public void Update(BandDTO band) {
		find(band.getCodigo());
		repository.save(mapper.toEntity(band));
	}

	@Override
	public void delete(Long code) {
		Band band = repository.findById(code).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
		if(!isManager(getToken(), band)) {
			throw new BandException("Apenas o dono desta banda pode realizar essa operação", HttpStatus.BAD_REQUEST.value());
		}
		
		repository.deleteById(code);
	}

	@Override
	public BandDTO find(Long code) {
		return repository.findById(code).map(mapper::toDTO).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
	}

	@Override
	public void associateMembers(Long codeBand, String emailMember, Boolean leader, Long instrumentCode, Long voiceCode) {
		String token = getToken();
		String msg = "Apenas o leader ou o dono desta banda pode realizar essa operação";
		
		UserDTO user = this.getUser(emailMember);
		Band band = repository.findById(codeBand).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
		
		if(!isManager(token, band)) {
			throw new BandException(msg, HttpStatus.BAD_REQUEST.value());
		}
		
		Instrument instrument = Objects.nonNull(instrumentCode) ? addInstrument(token, instrumentCode) : null;
		Voice voice = Objects.nonNull(voiceCode) ? addVoice(token, voiceCode) : null;
		
		BandInfo bandInfo = bandInfo(user, band);
		bandInfo.setInstrument(instrument);
		bandInfo.setVoice(voice);
		
		if(leader) {
			bandInfo.setLeader(LeaderEnum.S);
			userClient.addRole(token, user.getCodigo(), PerfilEnum.LEADER_BAND.getCodigo());
		} else {
			bandInfo.setLeader(LeaderEnum.N);
		}
		
		bandInfoRepository.save(bandInfo);
	} 

	@Override
	public void disassociateMembers(Long codeBand, String emailMember) {
		Band band = repository.findById(codeBand).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
		String msg = "Apenas o leader ou o dono desta banda pode realizar essa operação";
		if(!isManager(getToken(), band)) {
			throw new BandException(msg, HttpStatus.BAD_REQUEST.value());
		}
		
		UserDTO user = this.getUser(emailMember);
		bandInfoRepository.disassociateMembers(codeBand, user.getCodigo());
		
		List<BandInfo> leaderBands = bandInfoRepository.findByBandsLeaderByUser(user.getCodigo(), LeaderEnum.S);
		if(leaderBands.isEmpty()) {
			userClient.disassociatePerfilUser(getToken(), user.getCodigo(), PerfilEnum.LEADER_BAND.getCodigo());
		}
	}
	
	@Override
	public List<MembersDTO> findMembers(Long codeBand) {
		List<MembersDTO> members = new ArrayList<>();
		Band band = repository.findById(codeBand).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
		
		List<BandInfo> result = band.getInfo();
		for (BandInfo ub : result) {
			
			String instrumentName = Objects.isNull(ub.getInstrument()) ? null : ub.getInstrument().getName();
			String voiceName = Objects.isNull(ub.getVoice()) ? null : ub.getVoice().getName();
			
			MembersDTO member = MembersDTO
					.builder()
					.name(ub.getCodigo().getUser().getName())
					.email(ub.getCodigo().getUser().getEmail())
					.leader(ub.getLeader())
					.intrumentName(instrumentName)
					.voiceName(voiceName)
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
		List<BandInfo> bands = new ArrayList<>();
		
		switch (user.getTypeUser()) {
		case PF:
			bands = bandInfoRepository.findAssociatedBandsByUser(user.getCodigo());
			break;
		case PJ:
			bands = bandInfoRepository.findAssociatedBandsByUserPj(user.getChavePj());
			break;
		default:
			throw new NotFoundException("Erro ao indentificar o tipo de pessoa");
		}
		
		bands.forEach(ub -> {
			result.add(mapper.toDTO(ub.getCodigo().getBand()));
		});
		
		return result;
	}
	
	private UserDTO getUser(String user) {
		String token = getToken();
		System.out.println("O token is: " + token);
		System.out.println("O user is: " + user);
		
		return Optional.ofNullable(userClient.findByEmail(token, user)).orElseThrow(() -> new UserException("User not found", HttpStatus.BAD_REQUEST.value()));
	}
	
	private BandInfo bandInfo(UserDTO user, Band band) {
		
		BandInfoPk pk = BandInfoPk
				.builder()
				.user(User.builder().codigo(user.getCodigo()).build())
				.band(band)
				.build();
		
		BandInfo userBand = BandInfo
				.builder()
				.codigo(pk)
				.build();
		return userBand;
	}
	
	private Instrument addInstrument(String token, Long instrumentCode) {
		try {
			InstrumentDTO res = instrumentClient.find(token, instrumentCode);
			return Instrument.builder().codigo(res.getCodigo()).build();
		} catch (Exception e) {
			throw new NotFoundException("Instrument not foud");
		}
	}
	
	private Voice addVoice(String token, Long voiceCode) {
		try {
			VoiceDTO res = voiceClient.find(token, voiceCode);
			return Voice.builder().codigo(res.getCodigo()).build();
		} catch (Exception e) {
			throw new EmptyResultDataAccessException("Voice not foud");
		}
	}
	
	private boolean isManager(String token, Band band) {
		User userAuth = AuthService.authenticated();
		UserDTO userModerator = userClient.findByKey(token, band.getAssociated().getChave());
		
		if(userAuth.hasRole(PerfilEnum.ADMIN)) {
			return true;
		} else if(userModerator.getEmail().equals(userAuth.getEmail())) {
			return true;
		} else {
			for (BandInfo i : band.getInfo()) {
				if(LeaderEnum.S.equals(i.getLeader())) {
					String emailLeader = i.getCodigo().getUser().getEmail();
					if(emailLeader.equals(userAuth.getEmail())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private String getToken() {
		return request.getHeader("Authorization");
	}
}

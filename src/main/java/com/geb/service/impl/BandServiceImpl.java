package com.geb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geb.client.InstrumentClient;
import com.geb.client.UserClient;
import com.geb.client.VoiceClient;
import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.handler.exception.UserException;
import com.geb.mapper.BandMapper;
import com.geb.model.Band;
import com.geb.model.BandInfo;
import com.geb.model.BandInfoPk;
import com.geb.model.Instrument;
import com.geb.model.User;
import com.geb.model.Voice;
import com.geb.model.dto.BandDTO;
import com.geb.model.dto.InstrumentDTO;
import com.geb.model.dto.MembersDTO;
import com.geb.model.dto.UserDTO;
import com.geb.model.dto.VoiceDTO;
import com.geb.model.enums.LeaderEnum;
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
		UserDTO user = this.getUser(dto.getMemberLeader());
		Band band = repository.save(mapper.toEntity(dto));
		BandInfo bandInfo = bandInfo(user, band);
		bandInfoRepository.save(bandInfo);
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
	public void associateMembers(Long codeBand, String emailMember, Boolean leader, Long instrumentCode, Long voiceCode) {
		String token = request.getHeader("Authorization");
		
		UserDTO user = this.getUser(emailMember);
		Band band = repository.findById(codeBand).orElseThrow(()-> new EmptyResultDataAccessException("Band not found"));
		
		Instrument instrument = null;
		Voice voice = null;

		if(Objects.nonNull(instrumentCode)) {
			try {
				InstrumentDTO res = instrumentClient.find(token, instrumentCode);
				instrument = Instrument.builder().codigo(res.getCodigo()).build();
			} catch (Exception e) {
				throw new EmptyResultDataAccessException("Instrument not foud");
			}
		}
		
		if(Objects.nonNull(voiceCode)) {
			try {
				VoiceDTO res = voiceClient.find(token, voiceCode);
				voice = Voice.builder().codigo(res.getCodigo()).build();
			} catch (Exception e) {
				throw new EmptyResultDataAccessException("Voice not foud");
			}
		}
		
		BandInfo bandInfo = bandInfo(user, band);
		bandInfo.setInstrument(instrument);
		bandInfo.setVoice(voice);
		bandInfo.setLeader(leader ? LeaderEnum.S : LeaderEnum.N);
		bandInfoRepository.save(bandInfo);
	} 
	
	@Override
	public void disassociateMembers(Long codeBand, String emailMember) {
		find(codeBand);
		UserDTO user = this.getUser(emailMember);
		bandInfoRepository.disassociateMembers(codeBand, user.getCodigo());
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
		List<BandInfo> bands = bandInfoRepository.findAssociatedBandsByUser(user.getCodigo());
		for (BandInfo ub : bands) {
			BandDTO band = mapper.toDTO(ub.getCodigo().getBand());
			result.add(band);
		}
		
		return result;
	}
	
	private UserDTO getUser(String user) {
		String token = request.getHeader("Authorization");
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
}

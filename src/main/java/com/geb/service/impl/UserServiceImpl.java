package com.geb.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.geb.client.InstrumentClient;
import com.geb.client.VoiceClient;
import com.geb.handler.exception.EmptyResultDataAccessException;
import com.geb.handler.exception.InternalErrorException;
import com.geb.handler.exception.NotFoundException;
import com.geb.handler.exception.UserException;
import com.geb.mapper.AddressMapper;
import com.geb.mapper.UserMapper;
import com.geb.model.Address;
import com.geb.model.Instrument;
import com.geb.model.PjAssociatedUser;
import com.geb.model.PjChave;
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
import com.geb.util.Util;

@Service
@Transactional
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
    	
    	try {
    		User entity = mapper.toEntity(user);
        	Set<Role> roles = new HashSet<>();
        	user.getRoles().add(PerfilEnum.USER.getCodigo());
        	
        	user.getRoles().forEach(role -> {
        		switch (user.getTypeUser()) {
        		case PJ:
        			roles.add(roleRepository.findByPerfil(PerfilEnum.from(role)));
        			roles.add(roleRepository.findByPerfil(PerfilEnum.MODERATOR));
					entity.setChave(addPjChave(entity));
        			break;
        		case PF:				
					roles.add(roleRepository.findByPerfil(PerfilEnum.from(role)));
					entity.setAssociated(addAssociatedUser(user, entity));
					break;	
        		default:
					throw new NotFoundException("Erro ao indentificar o tipo de pessoa");
        		}
    		}); 
        	
        	
        	if(Objects.nonNull(user.getAddress())) {
        		Address address = AddressMapper.INSTANCE.toEntity(user.getAddress());
        		address.setUser(entity);
        		entity.setAddress(address);
        	}
        	
        	entity.setRoles(roles);
        	repo.save(entity);
        	
		} catch (Exception e) {
			throw new UserException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
		}
    }

	@Override
    public void update(UserDTO user) {
    	if (repo.existsByEmail(user.getEmail())) {
			throw new UserException("User not found", HttpStatus.NOT_FOUND.value());
		}
    	AuthService.authenticated(user.getEmail());
    	
    	User entity = mapper.toEntity(user);
    	if(Objects.nonNull(user.getAddress())) {
    		entity.setAddress(AddressMapper.INSTANCE.toEntity(user.getAddress()));
    	}
    	
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
	
	@Override
	public void addRole(Long code, String role) {
		AuthService.authAdminOrModerator();
		User user = repo.findById(code).orElseThrow(()-> new UserException("User not found", HttpStatus.NOT_FOUND.value()));
		Set<Role> roles = user.getRoles();
		roles.add(roleRepository.findByPerfil(PerfilEnum.from(role)));
		user.setRoles(roles);
    	repo.save(user);
	}
	
	@Override
	public void disassociatePerfilUser(Long user, String role) {
		Role perfil = roleRepository.findByPerfil(PerfilEnum.from(role));
		roleRepository.disassociatePerfilUser(user, perfil.getCodigo());
	}
	
	@Override
	public UserDTO findByChave(String chave) {
		return repo.findByChaveChave(chave).map(mapper::toDTO)
				.orElseThrow(()-> new UserException("key not found", HttpStatus.NOT_FOUND.value()));
	}
	
	private PjChave addPjChave(User entity) {
		try {
			return PjChave
					.builder()
					.chave(Util.generateKeyPJ())
					.user(entity)
					.build();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new InternalErrorException("Erro ao gerar chave PJ");
		}
	}

	private PjAssociatedUser addAssociatedUser(UserDTO user, User entity) {
		PjAssociatedUser res = null;
		if(StringUtils.isNotBlank(user.getChavePj())) {
			if(Objects.nonNull(findByChave(user.getChavePj()))) {
				res = PjAssociatedUser
						.builder()
						.chave(user.getChavePj())
						.user(entity)
						.build();
				
				entity.setAssociated(res);
			}
		}
		return res;
	}
}

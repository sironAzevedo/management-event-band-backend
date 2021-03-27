package com.geb.model.dto;

import java.io.Serializable;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CredenciaisDTO extends Authenticator implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(email, senha);
	}

}

package com.geb.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;

import com.geb.handler.exception.AuthorizationException;
import com.geb.handler.exception.UserException;
import com.geb.model.User;
import com.geb.model.enums.PerfilEnum;

public final class AuthService {

	public static User authenticated() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new UserException(e.getLocalizedMessage());
		}
	}

	public static void authenticated(String email) {
		User user = authenticated();

		if (user == null || !user.hasRole(PerfilEnum.ROLE_ADMIN) && !email.equals(user.getEmail())) {
			throw new AuthorizationException("Acesso negado");
		}
	}
}

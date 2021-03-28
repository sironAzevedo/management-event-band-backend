package com.geb.config.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geb.model.User;
import com.geb.model.dto.CredenciaisDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;

	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {

		try {
			CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
					creds.getPassword(), new ArrayList<>());

			return authManager.authenticate(authToken);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			throw new AuthenticationCredentialsNotFoundException(e.getLocalizedMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String username = ((User) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		
		final String bearerToken = "Bearer " + token;
		final String tokenBody = "{\"token\": \"" + bearerToken + "\"}";
		res.setContentType("application/json");
		res.getWriter().append(tokenBody);
		res.addHeader("Authorization", bearerToken);
		res.addHeader("access-control-expose-headers", "Authorization");
	}

	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();
			return "{ \"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
		}
	}
}

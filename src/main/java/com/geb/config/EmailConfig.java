package com.geb.config;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.geb.model.dto.CredenciaisDTO;

@Configuration
public class EmailConfig {

	@Value("${mail.smtp.host}")
	private String host;
	@Value("${mail.smtp.port}")
	private String port;
	@Value("${mail.smtp.from}")
	private String from;
	@Value("${mail.username}")
	private String userName;
	@Value("${mail.password}")
	private String password;
	@Value("${mail.transport.protocol}")
	private String protocol;

	@Bean
	public Session getSession() {
		Session session = Session.getInstance(properties(), auth());
		session.setDebug(true);
		return session;
	}

	protected Authenticator auth() {
		return new CredenciaisDTO(userName, password);
	} 

	protected Properties properties() {
		Properties prop = new Properties();
		prop.put("host", host);
		prop.put("port", port);
		prop.put("from", from);
		prop.put("user", userName);
		prop.put("password", password);
		prop.put("protocol", protocol);
		return prop;
	}
}

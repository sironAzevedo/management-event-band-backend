package com.geb.service.impl;

import static com.geb.util.EmailUtil.getAddress;
import static com.geb.util.EmailUtil.getAnexo;
import static com.geb.util.EmailUtil.transport;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geb.handler.exception.EmailException;
import com.geb.model.MensagemDTO;
import com.geb.service.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {
	
	@Autowired
	private Session session;

	@Override
	public void sendEmail(MensagemDTO dto) {
		try {
			Message msg = new MimeMessage(session);
			msg.setSubject(dto.getAssunto());
			msg.setFrom(new InternetAddress(session.getProperty("from")));

			if (dto.getDestinatarios() != null && !dto.getDestinatarios().isEmpty()) {
				msg.setRecipients(Message.RecipientType.TO, getAddress(dto.getDestinatarios()));
			} else {
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(dto.getDestinatario()));
			}

			String anexo = null;

			if (dto.isAnexo()) {
				anexo = "C:\\Users\\Desenvolvimento\\Documents\\SIRON AZEVEDO SANTOS DA SILVA.pdf";
				msg.setContent(getAnexo(msg, dto.getTexto(), anexo));
			} else {
				msg.setText(dto.getTexto());
			}

			transport(session, msg, session.getProperty("protocol"), session.getProperty("host"),
					session.getProperty("userName"), session.getProperty("password"));
		} catch (Exception e) {
			throw new EmailException("E-mail não enviado: " + e.getLocalizedMessage());
		}
	}

}

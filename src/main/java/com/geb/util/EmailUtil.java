package com.geb.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.geb.handler.exception.EmailException;
import com.geb.model.dto.EmailDTO;

public final class EmailUtil {

	private EmailUtil() {
		super();
	}

	public static final Address[] getAddress(Set<EmailDTO> destinatarios) throws AddressException {
		List<EmailDTO> destinatario = new ArrayList<>(destinatarios);
		InternetAddress[] address = new InternetAddress[destinatario.size()];
		for (int i = 0; i < destinatario.size(); i++) {
			address[i] = new InternetAddress(destinatario.get(i).getEmail());
		}
		return address;
	}

	public static final Multipart getAnexo(Message msg, String mensagem, String anexo) {
		MimeBodyPart mpb = new MimeBodyPart();
		Multipart mp = new MimeMultipart();
		MimeBodyPart mbpAnexo = new MimeBodyPart();

		try {
			mpb.setText(mensagem);
			mp.addBodyPart(mpb);
			File file = new File(anexo);
			mbpAnexo.setDataHandler(new DataHandler(new FileDataSource(file)));
			mbpAnexo.setFileName(file.getName());
			mp.addBodyPart(mbpAnexo);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return mp;
	}

	public static final void transport(Session session, Message msg, String protocol, String host,
			String userName, String password) {
		Transport transport;

		try {
			transport = session.getTransport(protocol);
			transport.connect(host, userName, password);
			msg.saveChanges();
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			throw new EmailException("E-mail não enviado" + e.getLocalizedMessage());
		}
	}
}

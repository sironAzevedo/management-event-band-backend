package com.geb.service;

import com.geb.model.MensagemDTO;

public interface IEmailService {
	
	void sendEmail(MensagemDTO msg);

}

package com.piotrmajcher.piwind.mobileappserver.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
	
	void sendMail(SimpleMailMessage mailMessage);
}

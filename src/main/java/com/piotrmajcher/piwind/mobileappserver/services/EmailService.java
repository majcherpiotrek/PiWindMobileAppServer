package com.piotrmajcher.piwind.mobileappserver.services;

import com.piotrmajcher.piwind.mobileappserver.services.exceptions.EmailServiceException;

public interface EmailService {
	
	void sendMail(String recipientAddress, String subject, String content) throws EmailServiceException;
}

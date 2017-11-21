package com.piotrmajcher.piwind.mobileappserver.services.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;
import com.piotrmajcher.piwind.mobileappserver.services.EmailService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.EmailServiceException;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);

    @Override
    @Async
    public void sendMail(String recipientAddress, String subject, String content) throws EmailServiceException {
        Email from = new Email("app81460570@heroku.com");
		Email to = new Email(recipientAddress);
		Content mailContent = new Content("text/plain", content);
		
		Mail mail = new Mail(from, subject, to, mailContent);
		
		SendGrid sg = new SendGrid(System.getenv("PIWIND_API_KEY"));
		Request request = new Request();
		
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
		    request.body = mail.build();
		    Response response = sg.api(request);
		    logger.info(response.statusCode);
		    logger.info(response.body);
		    logger.info(response.headers);
		} catch (IOException e) {
		      throw new EmailServiceException(e.getMessage());
	    }
    }

}

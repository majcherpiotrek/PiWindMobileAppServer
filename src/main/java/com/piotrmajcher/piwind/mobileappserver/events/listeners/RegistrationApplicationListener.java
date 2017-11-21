package com.piotrmajcher.piwind.mobileappserver.events.listeners;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;
import com.piotrmajcher.piwind.mobileappserver.events.OnRegistrationCompleteEvent;
import com.piotrmajcher.piwind.mobileappserver.services.EmailService;
import com.piotrmajcher.piwind.mobileappserver.services.UserService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.EmailServiceException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.UserTO;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

@Component
public class RegistrationApplicationListener implements ApplicationListener<OnRegistrationCompleteEvent>{
	
	private final UserService userService;

    private final EmailService emailService;
    
    @Autowired
    public RegistrationApplicationListener(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }
    
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		try {
			sendRegistrationConfirmationEmail(event);
		} catch (EmailServiceException e) {
			// TODO Handle informing user about the error and delete account
			e.printStackTrace();
		}	
	}
	
	private void sendRegistrationConfirmationEmail(OnRegistrationCompleteEvent event) throws EmailServiceException {
		UserTO user = event.getUser();
        VerificationToken token = userService.createAndSaveVerificationToken(user);
        
		emailService.sendMail(user.getEmail(), "Piwind - account confirmation", "Your account confirmation token: " + token.getToken());
    }

}

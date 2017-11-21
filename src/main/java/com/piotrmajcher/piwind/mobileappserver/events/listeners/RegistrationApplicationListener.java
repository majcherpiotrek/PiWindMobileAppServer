package com.piotrmajcher.piwind.mobileappserver.events.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;
import com.piotrmajcher.piwind.mobileappserver.events.OnRegistrationCompleteEvent;
import com.piotrmajcher.piwind.mobileappserver.services.EmailService;
import com.piotrmajcher.piwind.mobileappserver.services.UserService;
import com.piotrmajcher.piwind.mobileappserver.web.dto.UserTO;

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
		sendRegistrationConfirmationEmail(event);	
	}
	
	private void sendRegistrationConfirmationEmail(OnRegistrationCompleteEvent event) {
        UserTO user = event.getUser();
        VerificationToken token = userService.createAndSaveVerificationToken(user);

        String recipientAddress = user.getEmail();
        String subject = "Piwind - registration confirmation";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("robolify@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Your account confirmation token: " + token.getToken());
        emailService.sendMail(email);
    }

}

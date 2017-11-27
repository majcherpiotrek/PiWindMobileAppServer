package com.piotrmajcher.piwind.mobileappserver;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;
import com.piotrmajcher.piwind.mobileappserver.repository.UserRepository;
import com.piotrmajcher.piwind.mobileappserver.services.UserService;
import com.piotrmajcher.piwind.mobileappserver.web.dto.UserTO;

@Component
public class DevelopmentDataLoader implements ApplicationRunner {

	@Autowired
	private UserService userService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		UserTO userTO = new UserTO();
		userTO.setEmail("example@email.com");
		userTO.setPassword("password");
		userTO.setMatchingPassword("password");
		userTO.setUsername("username");
		
		userService.registerUser(userTO);
        VerificationToken token = userService.createAndSaveVerificationToken(userTO);
        userService.confirmUser(token.getToken());
	}

}

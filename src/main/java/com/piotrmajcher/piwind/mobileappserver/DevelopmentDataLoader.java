package com.piotrmajcher.piwind.mobileappserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;
import com.piotrmajcher.piwind.mobileappserver.repository.UserRepository;
import com.piotrmajcher.piwind.mobileappserver.services.UserService;

@Component
public class DevelopmentDataLoader implements ApplicationRunner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		UserEntity user = new UserEntity();
		user.setEmail("example@email.com");
		user.setPassword("password");
		user.setUsername("username");
		user.setEnabled(false);
		
        VerificationToken token = userService.createAndSaveVerificationToken();
        user.setToken(token);
        
        userRepository.save(user);
        userService.confirmUser(token.getToken());
	}

}

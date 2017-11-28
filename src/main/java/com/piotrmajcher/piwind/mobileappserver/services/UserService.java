package com.piotrmajcher.piwind.mobileappserver.services;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.RegistrationException;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.RetrievePasswordException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.UserTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.RetrievePasswordTO;

public interface UserService {

	void registerUser(UserTO userTO) throws RegistrationException;

	void confirmUser(String token) throws RegistrationException;

    void retrievePasswordByUsername(String username) throws RetrievePasswordException;

    void retrievePasswordByEmail(String email) throws RetrievePasswordException;

    void changeRetrievedPassword(RetrievePasswordTO retrievePasswordTO) throws RetrievePasswordException;

	UUID getUserId(String username);

	VerificationToken createAndSaveVerificationToken();
	
	UserDetailsService getUserDetailsService();
}

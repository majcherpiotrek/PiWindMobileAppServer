package com.piotrmajcher.piwind.mobileappserver.services.impl;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import com.piotrmajcher.piwind.mobileappserver.domain.UserEntity;
import com.piotrmajcher.piwind.mobileappserver.domain.VerificationToken;
import com.piotrmajcher.piwind.mobileappserver.repository.UserRepository;
import com.piotrmajcher.piwind.mobileappserver.repository.VerificationTokenRepository;
import com.piotrmajcher.piwind.mobileappserver.services.EmailService;
import com.piotrmajcher.piwind.mobileappserver.services.UserService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.EmailServiceException;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.PasswordsNotMatchingException;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.RegistrationException;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.RetrievePasswordException;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.UserEmailConstraintViolationException;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.UsernameConstraintViolationException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.RetrievePasswordTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.UserTO;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	private static final String DUPLICATE_USER_EMAIL_ERROR = "User with this email address already exists.";
    private static final String DUPLICATE_USERNAME_ERROR = "User with this username already exists.";
    private static final String PASSWORDS_NOT_MATCHING_ERROR = "Entered passwords are not matching.";;

    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final VerificationTokenRepository verificationTokenRepository;
    
    private final EmailService emailService;
    
    @Autowired
    public UserServiceImpl(
    		UserRepository userRepository, 
    		PasswordEncoder passwordEncoder, 
    		VerificationTokenRepository verificationTokenRepository, 
    		EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
    }
    
	@Override
	public void registerUser(UserTO userTO) throws RegistrationException {
		UserEntity user = new UserEntity();
		try {
            validateUserRegistrationData(userTO);
            user.setUsername(userTO.getUsername());
            user.setPassword(userTO.getPassword());
            user.setEmail(userTO.getEmail());
            VerificationToken token = createAndSaveVerificationToken();
            user.setToken(token);
            saveUser(user);
            emailService.sendMail(user.getEmail(), "Piwind - account confirmation", "Your account confirmation token: " + token.getToken());
        } catch (Exception e) {
        	
        	if (e instanceof EmailServiceException) {
        		userRepository.delete(user);
        	}
        	
            String errorMessage = null;
            if (e instanceof TransactionSystemException) {
                Throwable cause = e.getCause();
                while ( (cause != null) && !(cause instanceof ConstraintViolationException) ) {
                    cause = cause.getCause();
                }

                if (cause != null) {
                    StringBuilder message = new StringBuilder();
                    Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)cause).getConstraintViolations();
                    for (ConstraintViolation<?> violation : violations) {
                        message.append(violation.getMessage());
                    }
                    errorMessage = message.toString();
                }
            }

            if (errorMessage == null) {
                errorMessage = e.getMessage();
            }

            logger.error("Exception occurred while trying to register new user: " + errorMessage + "\n");
            e.printStackTrace();
            throw new RegistrationException(errorMessage);
        }
	}

	@Override
	public VerificationToken createAndSaveVerificationToken() {
		VerificationToken userToken = new VerificationToken();
        userToken.setToken(generateToken());
        return verificationTokenRepository.save(userToken);
	}

	@Override
	public void confirmUser(String token) throws RegistrationException {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        Date now = new Date();
        if (verificationToken == null || now.after(verificationToken.getExpiryDate()) ) {
            throw new RegistrationException("We are sorry, your token is invalid or expired.");
        }

        UserEntity user = findByVerificationToken(verificationToken);

        if (user == null) {
            throw new RegistrationException("Could not verify - user does not exist!");
        }
        
        user.setEnabled(true);
        user.setToken(null);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        logger.info("User \'" + user.getUsername() + "\' has been verified!");
		
	}

	@Override
	public void retrievePasswordByUsername(String username) throws RetrievePasswordException {
		UserEntity user = userRepository.findByUsername(username);
        retrievePassword(user);	
	}

	@Override
	public void retrievePasswordByEmail(String email) throws RetrievePasswordException {
		UserEntity user = userRepository.findByEmail(email);
        retrievePassword(user);
	}

	@Override
	public void changeRetrievedPassword(RetrievePasswordTO retrievePasswordTO) throws RetrievePasswordException {
		validateRetrievedPassword(retrievePasswordTO);

        VerificationToken verificationToken = verificationTokenRepository.findByToken(retrievePasswordTO.getToken());
        
        if (verificationToken == null) {
            throw new RetrievePasswordException("We are sorry, your token is invalid or expired.");
        }
        
        UserEntity user = userRepository.findByToken(verificationToken);
        if (user == null) {
        	verificationTokenRepository.delete(verificationToken);
            throw new RetrievePasswordException("We are sorry, there is no user connected to this token!");
        }

        try {
        	user.setPassword(passwordEncoder.encode(retrievePasswordTO.getPassword()));
        	user.setToken(null);
            userRepository.save(user);
            verificationTokenRepository.delete(verificationToken);
        } catch (Exception e) {
            throw new RetrievePasswordException(e.getMessage());
        }	
	}

	@Override
	public UUID getUserId(String username) {
		UserEntity user = userRepository.findByUsername(username);
		return user == null ? null : user.getId();
	}
	
	@Bean
	@Override
	public UserDetailsService getUserDetailsService() {
		
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				UserEntity user = userRepository.findByUsername(username);
				
				if (user == null) {
					throw new UsernameNotFoundException(username);
				}
				return new User(user.getUsername(), user.getPassword(), emptyList());
			}
		};
	}
	
	private void retrievePassword(UserEntity user) throws RetrievePasswordException {
        if (user == null) {
            throw new RetrievePasswordException("User with username \'" + user.getUsername() + "\' does not exist!");
        }

        VerificationToken token = user.getToken();
        if (token != null) {
        	user.setToken(null);
        	userRepository.save(user);
            verificationTokenRepository.delete(token);
        }

        token = new VerificationToken();
        token.setToken(generateToken());

        verificationTokenRepository.save(token);
        user.setToken(token);
        userRepository.save(user);

        sendRetrieveTokenEmail(token.getToken(), user);
    }
    
	private void validateRetrievedPassword(RetrievePasswordTO retrievePasswordTO) {
        Assert.notNull(retrievePasswordTO, "Please provide new password!");
        Assert.isTrue(retrievePasswordTO.getPassword().equals(retrievePasswordTO.getMatchingPassword()), "The passwords don't match!");
    }

    private UserEntity findByVerificationToken(VerificationToken token) {
        return userRepository.findByToken(token);
    }

    private void validateUserRegistrationData(UserTO userTO) throws UserEmailConstraintViolationException, UsernameConstraintViolationException, PasswordsNotMatchingException {
        Assert.notNull(userTO.getPassword(), "Please provide a password");
        Assert.isTrue(userTO.getPassword().length() >= 8, "The password must be at least 8 characters" );
        UserEntity user = findByEmail(userTO.getEmail());

        if (user != null) {
            throw new UserEmailConstraintViolationException(DUPLICATE_USER_EMAIL_ERROR);
        }

        user = findByUsername(userTO.getUsername());

        if (user != null) {
            throw new UsernameConstraintViolationException(DUPLICATE_USERNAME_ERROR);
        }

        if (!userTO.getPassword().equals(userTO.getMatchingPassword())) {
            throw new PasswordsNotMatchingException(PASSWORDS_NOT_MATCHING_ERROR);
        }
    }

    @Transactional
    private void saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("Saved new user: " + user.getUsername());
    }

    private void sendRetrieveTokenEmail(String token, UserEntity user) {
        String recipientAddress = user.getEmail();
        String subject = "Piwind - retrieve password";

        try {
			emailService.sendMail(recipientAddress, subject, "Copy this token to change your password: " + token);
		} catch (EmailServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    private String generateToken() {
    	StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
        	int digit = ThreadLocalRandom.current().nextInt(0, 10);
        	sb.append(digit);
        }
        return sb.toString();
    }
}

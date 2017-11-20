package com.piotrmajcher.piwind.mobileappserver.web.rest;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piotrmajcher.piwind.mobileappserver.events.OnRegistrationCompleteEvent;
import com.piotrmajcher.piwind.mobileappserver.events.publishers.RegistrationEventsPublisher;
import com.piotrmajcher.piwind.mobileappserver.services.UserService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.RegistrationException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.JsonResponseTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.UserTO;

@CrossOrigin
@RestController
@RequestMapping("/")
public class RegistrationController {
	
	private static final Logger logger = Logger.getLogger(RegistrationController.class);

    private final static String REGISTRATION_SUCCESS_MSG = "Registration successful!";
    
    private final UserService userService;

    private final RegistrationEventsPublisher registrationEventsPublisher;

	
    @Autowired
    public RegistrationController(UserService userService, RegistrationEventsPublisher registrationEventsPublisher) {
        this.userService = userService;
        this.registrationEventsPublisher = registrationEventsPublisher;
    }
    
    @CrossOrigin
    @PostMapping(value = "/register-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonResponseTO> registerUser(HttpServletRequest request, @RequestBody UserTO userTO) {
        logger.info("Received request: " + userTO.toString());
        JsonResponseTO response = new JsonResponseTO();
        try {

            userService.registerUser(userTO);
            registrationEventsPublisher.publishEvent(new OnRegistrationCompleteEvent(userTO, request.getLocale()));
        } catch (RegistrationException e) {
        	
        	response.setErr(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        
        response.setMsg(REGISTRATION_SUCCESS_MSG);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping(value = "/confirm/{verificationToken}")
    public ResponseEntity<String> confirmUser(@PathVariable String verificationToken) {
        try {
            userService.confirmUser(verificationToken);
        } catch (RegistrationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Your account has been activated!", HttpStatus.OK);
    }
}

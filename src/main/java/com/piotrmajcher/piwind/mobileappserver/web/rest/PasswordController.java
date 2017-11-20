package com.piotrmajcher.piwind.mobileappserver.web.rest;

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

import com.piotrmajcher.piwind.mobileappserver.services.UserService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.RetrievePasswordException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.RetrievePasswordTO;

@RestController
@RequestMapping("/password")
@CrossOrigin
public class PasswordController {

    private final UserService userService;


    @Autowired
    public PasswordController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/retrieve/username/{username}")
    @CrossOrigin
    public ResponseEntity<String> retrievePasswordByUsername(@PathVariable String username) {

        try {
            userService.retrievePasswordByUsername(username);
        } catch (RetrievePasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("We have sent a confirmation token to your email", HttpStatus.OK);
    }

    @GetMapping("/retrieve/email/{email}")
    @CrossOrigin
    public ResponseEntity<String> retrievePasswordByEmail(@PathVariable String email) {

        try {
            userService.retrievePasswordByEmail(email);
        } catch (RetrievePasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("We have sent a confirmation token to your email", HttpStatus.OK);
    }

    @PostMapping(value = "/retrieve/newpassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> changeRetrievedPassword(@RequestBody RetrievePasswordTO retrievePasswordTO) {

        try {
            userService.changeRetrievedPassword(retrievePasswordTO);
        } catch (RetrievePasswordException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Your password has been changed", HttpStatus.OK);
    }

}

package com.piotrmajcher.piwind.mobileappserver.services.exceptions;

public class UserEmailConstraintViolationException extends Exception {
	public UserEmailConstraintViolationException(String msg) {
		super(msg);
	}
}

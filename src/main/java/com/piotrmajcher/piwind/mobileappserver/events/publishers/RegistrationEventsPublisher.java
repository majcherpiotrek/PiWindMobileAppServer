package com.piotrmajcher.piwind.mobileappserver.events.publishers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.events.OnRegistrationCompleteEvent;

@Component
public class RegistrationEventsPublisher {
	
	private static final Logger logger = Logger.getLogger(RegistrationEventsPublisher.class);
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	public RegistrationEventsPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
		logger.info("Publishing new user registration event: " + onRegistrationCompleteEvent.getUser().toString());
		applicationEventPublisher.publishEvent(onRegistrationCompleteEvent);
	}

}

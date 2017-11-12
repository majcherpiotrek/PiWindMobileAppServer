package com.piotrmajcher.piwind.mobileappserver.events.publishers;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.events.OnMeteoDataUpdateReceivedEvent;
import com.piotrmajcher.piwind.mobileappserver.events.OnSnapshotUpdateReceivedEvent;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTOAndroid;

@Component
public class UpdateEventsPublisher {
	private static final Logger logger = Logger.getLogger(UpdateEventsPublisher.class);
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	public UpdateEventsPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishMeteoDataUpdateEvent(final UUID stationId, final MeteoDataTOAndroid updatedData) {
		logger.info("Publishing new update data: " + updatedData.toString());
		OnMeteoDataUpdateReceivedEvent updateEvent = new OnMeteoDataUpdateReceivedEvent(this, stationId, updatedData);
		applicationEventPublisher.publishEvent(updateEvent);
	}
	
	public void publishSnapshotUpdateEvent(final UUID stationId, final byte[] snapshot) {
		logger.info("Publishing new snapshot update event");
		OnSnapshotUpdateReceivedEvent snapshotEvent = new OnSnapshotUpdateReceivedEvent(this, stationId, snapshot);
		applicationEventPublisher.publishEvent(snapshotEvent);
	}
}

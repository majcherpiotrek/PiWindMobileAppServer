package com.piotrmajcher.piwind.mobileappserver.events;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;

public class OnMeteoDataUpdateReceivedEvent extends ApplicationEvent {
	
	private UUID stationId;
	private MeteoDataTO updatedData;
	
	public OnMeteoDataUpdateReceivedEvent(Object source, UUID stationId, MeteoDataTO updatedData) {
		super(source);
		this.updatedData = updatedData;
		this.stationId = stationId;
	}

	public MeteoDataTO getUpdatedData() {
		return updatedData;
	}
	
	public UUID getStationId() {
		return stationId;
	}
}

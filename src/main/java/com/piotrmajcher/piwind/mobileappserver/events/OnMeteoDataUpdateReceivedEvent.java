package com.piotrmajcher.piwind.mobileappserver.events;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTOAndroid;

public class OnMeteoDataUpdateReceivedEvent extends ApplicationEvent {
	
	private UUID stationId;
	private MeteoDataTOAndroid updatedData;
	
	public OnMeteoDataUpdateReceivedEvent(Object source, UUID stationId, MeteoDataTOAndroid updatedData) {
		super(source);
		this.updatedData = updatedData;
		this.stationId = stationId;
	}

	public MeteoDataTOAndroid getUpdatedData() {
		return updatedData;
	}
	
	public UUID getStationId() {
		return stationId;
	}
}

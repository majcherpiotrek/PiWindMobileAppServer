package com.piotrmajcher.piwind.mobileappserver.events.listeners;

import java.util.EventListener;
import java.util.UUID;

import com.piotrmajcher.piwind.mobileappserver.events.OnMeteoDataUpdateReceivedEvent;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTOAndroid;

public interface MeteoDataUpdatePublishEventListener extends EventListener {
	void onMeteoDataUpdatedPublishedEvent(MeteoDataTOAndroid updatedData);
	UUID getListeningStationId();
}

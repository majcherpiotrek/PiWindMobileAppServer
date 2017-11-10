package com.piotrmajcher.piwind.mobileappserver.events.listeners;

import java.util.EventListener;

import com.piotrmajcher.piwind.mobileappserver.events.OnMeteoDataUpdateReceivedEvent;

public interface MeteoDataUpdatePublishEventListener extends EventListener {
	void onMeteoDataUpdatedPublishedEvent(OnMeteoDataUpdateReceivedEvent event);
}

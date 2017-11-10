package com.piotrmajcher.piwind.mobileappserver.events.listeners;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.events.OnMeteoDataUpdateReceivedEvent;

@Component
public class MeteoDataUpdateApplicationListener implements ApplicationListener<OnMeteoDataUpdateReceivedEvent> {
	private static final Logger logger = Logger.getLogger(MeteoDataUpdateApplicationListener.class);
	private List<MeteoDataUpdatePublishEventListener> listeners;
	
	@Autowired
	public MeteoDataUpdateApplicationListener() {
		this.listeners = new LinkedList<>();
	}
	
	@Override
	public void onApplicationEvent(OnMeteoDataUpdateReceivedEvent event) {
		logger.info("Intercepted a meteo data update event for station wit id " + event.getStationId());
		for (MeteoDataUpdatePublishEventListener listener : listeners) {
			listener.onMeteoDataUpdatedPublishedEvent(event);
		}
	}
	
	public void addMeteoDataUpdatePublishEventListener(MeteoDataUpdatePublishEventListener listener) {
			this.listeners.add(listener);
	}
	
	public void removeMeteoDataUpdatePublishEventListener(MeteoDataUpdatePublishEventListener listener) {
		this.listeners.remove(listener);
	}

}

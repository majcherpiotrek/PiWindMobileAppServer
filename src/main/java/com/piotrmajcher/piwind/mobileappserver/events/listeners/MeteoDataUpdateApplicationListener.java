package com.piotrmajcher.piwind.mobileappserver.events.listeners;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.events.OnMeteoDataUpdateReceivedEvent;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTOAndroid;

@Component
public class MeteoDataUpdateApplicationListener implements ApplicationListener<OnMeteoDataUpdateReceivedEvent> {
	private static final Logger logger = Logger.getLogger(MeteoDataUpdateApplicationListener.class);
	private List<MeteoDataUpdatePublishEventListener> listeners;
	private Map<UUID, MeteoDataTOAndroid> latestMeasurementsMap;
	
	@Autowired
	public MeteoDataUpdateApplicationListener() {
		this.listeners = new LinkedList<>();
		this.latestMeasurementsMap = new HashMap<>();
	}
	
	@Override
	public void onApplicationEvent(OnMeteoDataUpdateReceivedEvent event) {
		logger.info("Intercepted a meteo data update event for station with id " + event.getStationId());
		MeteoDataTOAndroid updatedData = event.getUpdatedData();
		latestMeasurementsMap.put(event.getStationId(), updatedData);
		
		for (MeteoDataUpdatePublishEventListener listener : listeners) {
			if (event.getStationId().equals(listener.getListeningStationId())) {
				listener.onMeteoDataUpdatedPublishedEvent(event.getUpdatedData());
			}
		}
	}
	
	public void addMeteoDataUpdatePublishEventListener(MeteoDataUpdatePublishEventListener listener) {
			this.listeners.add(listener);
			MeteoDataTOAndroid latestMeasurement = latestMeasurementsMap.get(listener.getListeningStationId());
			if (latestMeasurement != null) {
				listener.onMeteoDataUpdatedPublishedEvent(latestMeasurement);
			}
	}
	
	public void removeMeteoDataUpdatePublishEventListener(MeteoDataUpdatePublishEventListener listener) {
		this.listeners.remove(listener);
	}

}

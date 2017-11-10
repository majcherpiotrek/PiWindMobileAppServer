package com.piotrmajcher.piwind.mobileappserver.scheduledtasks;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.events.publishers.MeteoDataUpdateEventPublisher;
import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoStationTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.TemperatureTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.WindSpeedTO;

@Component
public class MeteoDataUpdateScheduledTask {
	private static final Logger logger = Logger.getLogger(MeteoDataUpdateScheduledTask.class);
	
	private MeteoStationService meteoStationService;
	private MeteoDataUpdateEventPublisher meteoDataUpdateEventPublisher;
	
	@Autowired
	public MeteoDataUpdateScheduledTask(MeteoStationService meteoStationService, MeteoDataUpdateEventPublisher meteoDataUpdateEventPublisher) {
		this.meteoStationService = meteoStationService;
		this.meteoDataUpdateEventPublisher = meteoDataUpdateEventPublisher;
	}
	
	// TODO the rate should be the synced with piwind refresh rate
	@Scheduled(fixedRate = 10000)
	private void getAndPublishMeteoDataUpdates() {
		List<MeteoStationTO> stationsList = meteoStationService.getAllStations();
		
		if (stationsList != null) {
			for (MeteoStationTO station : stationsList) {
				try {
					UUID stationId = station.getId();
					MeteoDataTO meteoData = meteoStationService.getLatestMeteoData(stationId);
					logger.info("Received meteo data: " + (meteoData != null ? meteoData.toString() : "null"));
					meteoDataUpdateEventPublisher.publishMeteoDataUpdateEvent(stationId, meteoData);
				} catch(MeteoStationServiceException e) {
					logger.error("Failed to update meteo data: " + e.getMessage());
				}
			}
		}
	}
}

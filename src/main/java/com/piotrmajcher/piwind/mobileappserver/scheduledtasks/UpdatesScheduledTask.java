package com.piotrmajcher.piwind.mobileappserver.scheduledtasks;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.piotrmajcher.piwind.mobileappserver.events.publishers.UpdateEventsPublisher;
import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.services.WeatherConditionsExpertService;
import com.piotrmajcher.piwind.mobileappserver.services.exceptions.MeteoStationServiceException;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTOAndroid;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoStationTO;

@Component
public class UpdatesScheduledTask {
	private static final Logger logger = Logger.getLogger(UpdatesScheduledTask.class);
	
	private MeteoStationService meteoStationService;
	private UpdateEventsPublisher updateEventsPublisher;
	private WeatherConditionsExpertService weatherconditionsExpertService;
	
	@Autowired
	public UpdatesScheduledTask(
			MeteoStationService meteoStationService, 
			UpdateEventsPublisher meteoDataUpdateEventPublisher,
			WeatherConditionsExpertService weatherconditionsExpertService) {
		this.meteoStationService = meteoStationService;
		this.updateEventsPublisher = meteoDataUpdateEventPublisher;
		this.weatherconditionsExpertService = weatherconditionsExpertService;
	}
	
	@Scheduled(fixedRate = 3000)
	private void getAndPublishSnapshotUpdates() {
		List<MeteoStationTO> stationsList = meteoStationService.getAllStations();
		
		if (stationsList != null) {
			for (MeteoStationTO station : stationsList ) {
				try {
					UUID stationId = station.getId();
					byte[] snapshot = meteoStationService.getLatestSnapshotFromStation(stationId);
					logger.info("Received new snapshot");
					updateEventsPublisher.publishSnapshotUpdateEvent(stationId, snapshot);
				} catch(MeteoStationServiceException e) {
					logger.error("Failed to update snapshot from station with id " + station.getId() + ":"  + e.getMessage());
				}
			}
		}
	}
	
	@Scheduled(fixedRate = 5000)
	private void getAndPublishMeteoDataUpdates() {
		List<MeteoStationTO> stationsList = meteoStationService.getAllStations();
		
		if (stationsList != null) {
			for (MeteoStationTO station : stationsList) {
				try {
					UUID stationId = station.getId();
					MeteoDataTO meteoData = meteoStationService.getLatestMeteoDataFromStation(stationId);
					logger.info("Received meteo data: " + (meteoData != null ? meteoData.toString() : "null"));
					updateEventsPublisher.publishMeteoDataUpdateEvent(stationId, convertToAndroidTO(meteoData, station));
				} catch(MeteoStationServiceException e) {
					logger.error("Failed to update meteo data from station with id " + station.getId() + ":"  + e.getMessage());
				}
			}
		}
	}
	
	private MeteoDataTOAndroid convertToAndroidTO(MeteoDataTO meteoData, MeteoStationTO station) {
		MeteoDataTOAndroid meteoDataTOAndroid = new MeteoDataTOAndroid();
		meteoDataTOAndroid.setTemperature(meteoData.getTemperature());
		meteoDataTOAndroid.setDateTime(convertLocalDateTimeToDate(meteoData.getDateTime()));
		meteoDataTOAndroid.setWindSpeed(meteoData.getWindSpeed());
		meteoDataTOAndroid.setWindDirectionDescription(
				weatherconditionsExpertService.getWindDirectionDescription(station.getBeachFacingDirection(), meteoData.getWindDirection())
				);
		meteoDataTOAndroid.setBeaufortCategoryDescription(
				weatherconditionsExpertService.getWindBeaufortCategoryDescription(meteoData.getWindSpeed())
				);
		meteoDataTOAndroid.setTemperatureConditionsDescription(
				weatherconditionsExpertService.getTemperatureConditionsDescription(meteoData.getTemperature())
				);
		return meteoDataTOAndroid;
		
	}
	private Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}

package com.piotrmajcher.piwind.mobileappserver.web.websocket;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.piotrmajcher.piwind.mobileappserver.events.listeners.MeteoDataUpdateApplicationListener;
import com.piotrmajcher.piwind.mobileappserver.events.listeners.MeteoDataUpdatePublishEventListener;
import com.piotrmajcher.piwind.mobileappserver.services.WeatherConditionsExpertService;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTOAndroid;


@Controller
public class MeteoDataRefreshController {
	
	private static final Logger logger = Logger.getLogger(MeteoDataRefreshController.class);
	
	private SimpMessagingTemplate template;
	private MeteoDataUpdateApplicationListener meteoDataUpdateListener;
	private WeatherConditionsExpertService weatherconditionsExpertService;
	
	@Autowired
	public MeteoDataRefreshController(
			SimpMessagingTemplate template, 
			MeteoDataUpdateApplicationListener meteoUpdateListener, 
			WeatherConditionsExpertService weatherconditionsExpertService) {
		this.template = template;
		this.meteoDataUpdateListener = meteoUpdateListener;
		this.weatherconditionsExpertService = weatherconditionsExpertService;
	} 
	@MessageMapping("/start-update")
    @SendTo("/update/updater-url")
	public void sayHello(String stationIdString) {
		UUID stationId = UUID.fromString(stationIdString.trim());
		logger.info("Station update request received. Station id:" + stationId);
		
		meteoDataUpdateListener.addMeteoDataUpdatePublishEventListener(new MeteoDataUpdatePublishEventListener() {
			
			@Override
			public void onMeteoDataUpdatedPublishedEvent(MeteoDataTOAndroid updatedData) {
				logger.info("Received the meteo data update event in controller : " + updatedData);
				fireUpdate(updatedData);
			}

			@Override
			public UUID getListeningStationId() {
				return stationId;
			}
		});
	}

	private void fireUpdate(MeteoDataTOAndroid meteoData) {
		logger.info("Sending update");
		template.convertAndSend("/update/updater-url", meteoData);
	}
}

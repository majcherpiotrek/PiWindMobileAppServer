package com.piotrmajcher.piwind.mobileappserver.web.websocket;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.config.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.piotrmajcher.piwind.mobileappserver.events.OnMeteoDataUpdateReceivedEvent;
import com.piotrmajcher.piwind.mobileappserver.events.listeners.MeteoDataUpdateApplicationListener;
import com.piotrmajcher.piwind.mobileappserver.events.listeners.MeteoDataUpdatePublishEventListener;
import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;


@Controller
public class MeteoDataRefreshController {
	
	private static final Logger logger = Logger.getLogger(MeteoDataRefreshController.class);
	
	private SimpMessagingTemplate template;
	private MeteoDataUpdateApplicationListener meteoDataUpdateListener;
	
	@Autowired
	public MeteoDataRefreshController(SimpMessagingTemplate template, MeteoDataUpdateApplicationListener meteoUpdateListener) {
		this.template = template;
		this.meteoDataUpdateListener = meteoUpdateListener;
	} 
	@MessageMapping("/start-update")
    @SendTo("/update/updater-url")
	public void sayHello(String stationIdString) {
		UUID stationId = UUID.fromString(stationIdString.trim());
		logger.info("Station update request received. Station id:" + stationId);
		
		meteoDataUpdateListener.addMeteoDataUpdatePublishEventListener(new MeteoDataUpdatePublishEventListener() {
			
			@Override
			public void onMeteoDataUpdatedPublishedEvent(OnMeteoDataUpdateReceivedEvent event) {
				logger.info("Received the meteo data update event in controller. Update for station with id " + event.getStationId());
				if (event.getStationId().equals(stationId)) {
					fireUpdate(event.getUpdatedData());
				}
			}
		});
	}

	private void fireUpdate(MeteoDataTO meteoData) {
		logger.info("Sending update");
		template.convertAndSend("/update/updater-url", meteoData);
	}
}

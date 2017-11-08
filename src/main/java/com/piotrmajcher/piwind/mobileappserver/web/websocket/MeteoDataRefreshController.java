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

import com.piotrmajcher.piwind.mobileappserver.services.MeteoStationService;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTO;


@Controller
public class MeteoDataRefreshController {
	
	private static final Logger logger = Logger.getLogger(MeteoDataRefreshController.class);
	
	private SimpMessagingTemplate template;
	private MeteoStationService meteoStationService;
	
	@Autowired
	public MeteoDataRefreshController(SimpMessagingTemplate template, MeteoStationService meteoStationService) {
		this.template = template;
		this.meteoStationService = meteoStationService;
	} 
	@MessageMapping("/start-update")
    @SendTo("/update/updater-url")
	public void sayHello(String stationId) {
		logger.info("Station update request received. Station id:" + stationId);
		
		UUID uuid = UUID.fromString(stationId.trim().replaceAll(" ", ""));
		MeteoUpdater updater = new MeteoUpdater(this, meteoStationService, uuid);
		new Thread(updater).run();
	}

	public void fireUpdate(MeteoDataTO meteoData) {
		logger.info("Sending update");
		template.convertAndSend("/update/updater-url", meteoData);
	}
}

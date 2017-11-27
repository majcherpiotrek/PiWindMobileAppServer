package com.piotrmajcher.piwind.mobileappserver.web.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.piotrmajcher.piwind.mobileappserver.events.listeners.MeteoDataUpdateApplicationListener;
import com.piotrmajcher.piwind.mobileappserver.events.listeners.MeteoDataUpdatePublishEventListener;
import com.piotrmajcher.piwind.mobileappserver.web.dto.MeteoDataTOAndroid;

@Component
public class MeteoDataUpdateWebsocketHandler extends TextWebSocketHandler{
	
	private static final Logger logger = Logger.getLogger(MeteoDataUpdateWebsocketHandler.class);
	
	List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	Map<WebSocketSession, MeteoDataUpdatePublishEventListener> sessionsListeners = new HashMap<>();
	
	private MeteoDataUpdateApplicationListener meteoDataUpdateApplicationListener;
	
	@Autowired
	public MeteoDataUpdateWebsocketHandler(
			MeteoDataUpdateApplicationListener meteoDataUpdateApplicationListener) {
		this.meteoDataUpdateApplicationListener = meteoDataUpdateApplicationListener;
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		UUID stationId = UUID.fromString(message.getPayload().trim());
		logger.info("Station meteo data update request received. Station id:" + stationId);
		
		MeteoDataUpdatePublishEventListener listener = createMeteoDataUpdateListenerForSession(session, stationId);
		meteoDataUpdateApplicationListener.addMeteoDataUpdatePublishEventListener(listener);
	}
	
	private MeteoDataUpdatePublishEventListener createMeteoDataUpdateListenerForSession(WebSocketSession session,
			UUID stationId) {
		return new MeteoDataUpdatePublishEventListener() {
			
			@Override
			public void onMeteoDataUpdatedPublishedEvent(MeteoDataTOAndroid updatedData) {
				if (session.isOpen()) {
					try {
						session.sendMessage(new TextMessage(updatedData.toString()));
						logger.info("Meteo data update sent!");
					} catch (IOException e) {
						logger.error("Failed to send meteo data update: " + e.getMessage());;
					}
				} else {
					logger.info("Session closed. Cleaning up ...");
					meteoDataUpdateApplicationListener.removeMeteoDataUpdatePublishEventListener(this);
					sessionsListeners.remove(session);
					sessions.remove(session);
				}
			}
			
			@Override
			public UUID getListeningStationId() {
				return stationId;
			}
		};
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}
}
